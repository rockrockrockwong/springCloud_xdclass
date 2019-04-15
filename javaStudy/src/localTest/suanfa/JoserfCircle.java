package localTest.suanfa;

import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫问题是个有名的问题：
 * N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。
 * 例如N=6，M=5，被杀掉的顺序是：5，4，6，2，3，1。
 */
public class JoserfCircle {


    public static void main(String[] args) {
        new JoserfCircle().letsGo(6,5);
    }

    public void letsGo(int total, int callIt){
        List<Gamer> gamers = new ArrayList<>();
        for(int i=0;i<total;i++){
            gamers.add(new Gamer(i));
        }
        for(int i = 0; i < total; i++){
            int previous = i - 1;
            int next = i + 1;
            if(i == 0){
                previous = total - 1;
            }
            if(i == total - 1){
                next = 0;
            }
            gamers.get(i).previous = gamers.get(previous);
            gamers.get(i).next = gamers.get(next);
        }

        int left = total;
        int cnt;
        int cntFrom=0;
        boolean marked;
        while (left > 1){

            cnt = 0;
            marked = false;
            while(!marked) {

                for (Gamer gamer: gamers) {
                    if(gamer.marked){
                        continue;
                    }
                    if(cntFrom != -1 && gamer.id!=cntFrom){
                        continue;
                    }
                    cntFrom = -1;
                    cnt++;
                    if(cnt == callIt){
                        gamer.marked = true;
                        left--;
                        marked = true;
                        cntFrom = getNextAvaliable(gamers,gamer.id);
                        System.out.println(gamer.name);
                        break;
                    }
                }

            }

        }
    }

    private int getNextAvaliable(List<Gamer> gamers,int curId){
        while(true){
            Gamer curG = gamers.get(curId);
            Gamer next = curG.next;
            if(!next.marked){
                return next.id;
            }
            curId = next.id;
        }
    }


    public class Gamer{
        public int id;
        public boolean marked=false;
        public Gamer next;
        public Gamer previous;
        public String name;
        public Gamer(int id){
            name = "name"+(id+1);
            this.id = id;
        }
    }

}
