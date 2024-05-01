import java.util.*;

public class Labirinto {
    private final int x;
    private final int y;
    private final int[][] maze;
    private final Random rand = new Random();

    public Labirinto(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
    }

    public void display() {
        int entrada = rand.nextInt(y); // random entrada
        int saida = rand.nextInt(y); // random saida

        // desenha maze
        for (int i = 0; i < y; i++) {
            // desenha as linhas impares
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            
            // desenha as linhas pares
            for (int j = 0; j < x; j++) {
                if (i == entrada && j == 0) {
                    System.out.print("    "); // remove a barra esquerda aleatória para entrada
                } else {
                    System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
                }
            }
            
            // desenha coluna final
            if (i == saida) {
                System.out.println(" "); // remove a barra direita aleatória para sair
            } else{
                System.out.println("|");
            }
        }
        
        // desenha linha linal
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));

        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;

            if (between(nx, x) && between(ny, y) && maze[nx][ny] == 0) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use o inicializador estático para resolver referências futuras
        static {
            N.opposite = S; S.opposite = N;
            E.opposite = W; W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public static void main(String[] args) {
        int x = args.length >= 2 ? Integer.parseInt(args[0]) : 8;
        int y = args.length >= 2 ? Integer.parseInt(args[1]) : 8;
        Labirinto maze = new Labirinto(x, y);
        maze.display();
    }
}
