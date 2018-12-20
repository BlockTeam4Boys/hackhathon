package web;
import agent.Block;
import agent.TUSURChain;
import agent.Wallet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

import static agent.TUSURChain.firstClient;
import static agent.TUSURChain.global;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/welcome")
public class RegisterController {

    @RequestMapping(method = GET)
    public String getWallet() throws IOException {
        if (TUSURChain.count == 0) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int s = TUSURChain.global.size();
                int max = Integer.MIN_VALUE;
                int i;
                for (i = 0; i < s; i++) {
                    if (TUSURChain.global.get(i).size() > max) {
                        max = TUSURChain.global.get(i).size();
                    }
                }
                for (int j = 0; j < s; j++) {
                    if (j != i) {
                        ArrayList<Block> ne = new ArrayList<>();
                        ne.addAll(0, global.get(max));

                    }
                }
            });
            thread.start();
        }
        Wallet wallet = new Wallet();
        return wallet.toString();
    }
}
