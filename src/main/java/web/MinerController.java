package web;

import agent.Block;
import agent.TUSURChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/miner")
public class MinerController {

    @RequestMapping(method = GET)
    public void becomeAMiner(@RequestParam("c") String coun) {
        Thread thread = new Thread(() -> {
            int s = TUSURChain.global.get(coun).size();
            Block block = new Block(TUSURChain.global.get(coun).get(s - 1).hash);
            while (TUSURChain.transactions.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            block.addTransaction(TUSURChain.transactions.peek());
            block.mineBlock(TUSURChain.difficulty);
        });
        thread.start();
        System.out.println("Start Mining");
    }
}