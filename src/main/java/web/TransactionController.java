package web;

import agent.Block;
import agent.TUSURChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/send")
public class TransactionController {

    @RequestMapping(method = GET)
    public void sendTSR(@RequestParam("from") String from, @RequestParam("whom") String whom, @RequestParam("many") float many) {
        int s = TUSURChain.global.get(from).size() - 1;
        Block block = new Block(TUSURChain.global.get(from).get(s).hash);
        block.addTransaction(TUSURChain.wallets.get(from).sendFunds(TUSURChain.wallets.get(whom).publicKey, many));
        System.out.println("Send trans");
    }
}