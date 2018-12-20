package web;

import agent.TUSURChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    @RequestMapping(method = GET)
    public String getMyWallet(@RequestParam("c") String coun) {
        System.out.println("get Wallet");
        return TUSURChain.wallets.get(coun).toString();
    }


}

