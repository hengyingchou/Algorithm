public class match{
    /*
    This code implements matching algorithm.
    */
    private int preference[] ;
    private boolean match ;
    private int result;
    private boolean propose[];
    
    match(){
        preference = new int[4]; 
        propose = new boolean[4]; 
        match = false; 
        result = 0;
    }

    public void visited(int i, boolean x){

        propose[i] = x;

    }

    public boolean get_vis(int i){

        return propose[i];

    }

    public void change_result(int i){
        result = i;
    }
    public int get_result(){
        return result;
    }
    public boolean getbool(){
        return match;
    }
    public void change_boolean( boolean det) {
        match = det;
    }
    public int get_preference_index(int i) {
            return preference[i];
    }
    public int get_preference_rank(int p, int N){
        int position = 0;
        for(int i = 0; i < N ; i++){
            if(preference[i] == p){
                position = i;
            }
        }
        return position;
    }

    public void insert_preference(int[] a) {
        preference = a ;
    }

    public void record(int m , int w, int m_, int w_ , int det, int[][] track){
        for(int i = 0 ; i < 20 ; i++){

            if(track[i][0] == 0 && track[i][1] == 0 && track[i][2] == 0 && track[i][3] == 0){
                track[i][0] = m ;
                track[i][1] = w ;
                track[i][2] = m_ ;
                track[i][3] = w_;
                track[i][4] = det;
                break;
            }
        }
    }
    public void algorithm(match[] a,match[] b, int k,int start, int[][] track, int N){

        int index = 0;
        for( int i = start ; i < 4 ; i ++){
        
            index = get_preference_index(i);
            if(b[index].getbool() == false && this.get_vis(i) == false){

                this.change_boolean(true);
                b[index].change_boolean(true);
                this.change_result(index);
                b[index].change_result(k);
                this.record( (k+1) , (index+1) , (index+1) , -1 , 1 , track );
                this.visited(i, true);
                break;

            }else if(b[index].getbool() == true && this.get_vis(i) == false){

                int j = b[index].get_result();
                int rank1 = b[index].get_preference_rank(j,N);
                int rank2 = b[index].get_preference_rank(k,N);

                if(rank2 > rank1){

                    this.record( k+1 , index+1 , index+1 , (j+1) , -1 , track );
                    this.visited(i, true);

                }else if(rank1 > rank2){

                    a[j].change_boolean(false);
                    this.change_boolean(true);
                    b[index].change_result(k);
                    this.change_result(index);
                    this.record( k+1 , index+1 , index+1 , j+1 , 1 , track);
                    this.visited(i, true);
                    break;
                }
            } 
        }              
    }

    public static void main(final String[] arg) {

        int N = 4;
        match[] w = new match[N];
        match[] m = new match[N];
        
        for(int i = 0 ; i < N ; i++) w[i] = new match();
        for(int i = 0 ; i < N ; i++) m[i] = new match();

        //int a[][] = new int[][]{ { 2, 1, 3, 0 }, { 0, 1, 3, 2 }, { 0, 1, 2, 3 }, { 0, 1, 2, 3 } };
        //int b[][] = new int[][]{ { 0, 2, 1, 3 }, { 2, 0, 3, 1 }, { 3, 2, 1, 0 }, { 2, 3, 1, 0 } }; 
        int a[][] = new int[][]{ { 0, 1, 2, 3 }, { 0, 2, 3, 1 }, { 1, 0, 2, 3 }, { 1, 0, 2, 3 } };
        int b[][] = new int[][]{ { 2, 3, 0, 1 }, { 0, 1, 3, 2 }, { 2, 3, 0, 1 }, { 0, 1, 2, 3 } }; 
        
        int [][] track = new int[20][5];

        for(int i = 0 ; i < N ; i++) w[i].insert_preference(a[i]);
        for(int i = 0 ; i < N ; i++) m[i].insert_preference(b[i]);
        for(int i =0 ; i < N ; i++){
            for(int j = 0 ; j < N ; j++){
                w[i].visited(j,false);
            }
        };
        
        int q = 0;
        int start = 0;
        int count = 0;
        while(w[0].getbool() == false || w[1].getbool() == false || w[2].getbool() == false || w[3].getbool() == false){

            if(w[q].getbool() == false){
            w[q].algorithm(w, m, q, start, track,N);
            }    
            if(q == 3){
                q = 0;
            }else{
                q++;
                
            }
            count++;
        }

        System.out.println("The solution is");
        System.out.println("[ " + (w[0].get_result()+1) + ", " + (w[1].get_result()+1) + ", " + (w[2].get_result()+1) + ", " + (w[3].get_result()+1) + " ]");
        System.out.println(" ");
        System.out.println("Trace: ");
        for(int i = 0 ; i < count ; i++){
            String str = "n";
            if(track[i][4] == -1) str = "Rejected";
            else if(track[i][4] == 1) str = "Accepted";
            System.out.println(track[i][0] + " proposes to " + track[i][1] + " [ " + track[i][2] + " , " + track[i][3] + " ], " + str);
            
        }
        System.out.println(count);
    }
}