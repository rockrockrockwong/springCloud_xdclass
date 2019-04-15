package localTest.suanfa;

public class FormatArray {

    int[][] b;
    int[][] a = {
            { 12, 26, 89, 78, 45,78,77 },
            { 22, 61, 90, 34, 16,90,91 },
            { 13, 30, 47, 29, 33,66,58 },
            { 17, 38, 19, 49, 57,22,71 },
            { 37, 82, 35, 21, 24,99,60 } };
    int ax=0,ay=0;

    public static void main(String[] args) {
        new FormatArray().process();
    }

    public void process() {
        //output the original array
        System.out.println("the original array:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "   ");
            }
            System.out.println();
        }
        System.out.println("====================");
        //full sort array
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                int n = j + 1;
                for (int m = i; m < a.length; m++) {
                    for (; n < a[i].length; n++) {
                        if (a[i][j] > a[m][n]) {
                            int min = a[m][n];
                            a[m][n] = a[i][j];
                            a[i][j] = min;
                        }
                    }
                    n = 0;
                }
            }
        }
        //output the sorted array and copy it to new array b
        System.out.println("the sorted array:");
        b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "   ");
                b[i][j] = a[i][j];
            }
            System.out.println();
        }
        System.out.println("====================");

        //format the array "b"
        System.out.println("the formatted array:");
        for(int i = b[0].length; i>0; i--){
            processXLine(i);
        }
        for(int i = 2;i<=b.length;i++){
            processYLine(i);
        }
        //output array "b"
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                System.out.print(b[i][j] + "   ");
            }
            System.out.println();
        }
    }

    private void processXLine(int line){
        if(line == b[0].length){
            b[0][line-1] = a[ax][ay];
            ay++;
            return;
        }

        int x=0,y=line - 1;
        while(x < b.length && y < b[0].length){
            if(ay == a[0].length ){ay = 0;ax++;}
            b[x][y] = a[ax][ay];
            x++;
            y++;
            ay++;
        }
    }

    private void processYLine(int line){
        if(line == b.length){
            if(ay == a[0].length ){ay = 0;ax++;}
            b[line-1][0] = a[ax][ay];
            ay++;
            return;
        }

        int x=line - 1,y=0;
        while(x < b.length && y < b[0].length){
            if(ay == a[0].length){ay = 0;ax++;}
            b[x][y] = a[ax][ay];
            x++;
            y++;
            ay++;
        }
    }


    /**
     * Joserf Issue
     *
     *
     *
     * **/



}
