package agent;

import org.bouncycastle.util.encoders.Base64;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Map;

import static agent.TUSURChain.*;

public class Wallet {
	static {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

	}

	public PrivateKey privateKey;
	public PublicKey publicKey;


	public Wallet() {
		generateKeyPair();
		wallets.put( new String(Base64.encode(this.publicKey.toString().getBytes())), this);
		if (count == 0) {
			ArrayList<Block> blockchain = new ArrayList<>();

			genesisTransaction = new Transaction(this.publicKey, this.publicKey, 100f, null);
			genesisTransaction.generateSignature(this.privateKey);	 //manually sign the genesis transaction
			genesisTransaction.transactionId = "0"; //manually set the transaction id
			genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
			UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
			Block genesis = new Block("0");
			genesis.addTransaction(genesisTransaction);
			blockchain.add(genesis);
			firstClient = new String(Base64.encode(this.publicKey.toString().getBytes()));

			global.put(new String(Base64.encode(this.publicKey.toString().getBytes())), blockchain);

		} else {
			ArrayList<Block> dummy = new ArrayList<>();
			dummy.addAll(0, global.get(firstClient));
			global.put(new String(Base64.encode(this.publicKey.toString().getBytes())), dummy);
		}
		count++;
	}

	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public float getBalance() {
		float total = 0;
        for (Map.Entry<String, TransactionOutput> item: TUSURChain.UTXOs.entrySet()){
        	TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
            	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
            	total += UTXO.value ;
            }
        }
		return total;
	}
	
	public Transaction sendFunds(PublicKey _recipient,float value ) {
		if(getBalance() < value) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if(total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
		newTransaction.generateSignature(privateKey);
		
		for(TransactionInput input: inputs){
			UTXOs.remove(input.transactionOutputId);
		}
		
		return newTransaction;
	}

	@Override
	public String toString() {


		String pr = new String(Base64.encode(privateKey.toString().getBytes()));
		String pu = new String(Base64.encode(publicKey.toString().getBytes()));
		System.out.println(pu);
		return  pr + ";" + pu + ";" + getBalance();
	}
}


