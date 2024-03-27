import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

enum TerrainType{
    SIMPLE,
    SWAMP,
    TREE,
    HILL
}

enum WarriorType{
    INFANTRY,
    ARCHER,
    RIDER
}

public class Battle {

    public static void main(String[] args) throws Exception {
        cls();

        City city = new City();
        Enemy enemy = new Enemy();
        Battlefield field = new Battlefield();
        city.setBattlefield(field);
        enemy.setBattlefield(field);

        city.hire();

        do{
            city.makeTurn();
            enemy.makeTurn();
        }while(city.stillAlive() && enemy.stillAlive());

        if(city.stillAlive()){
            System.out.println("Congratulations, your city survived!");
        }
        else if(enemy.stillAlive()){
            System.out.println("What a pity, city is lost T_T");
        }
        else{
            System.out.println("Alas, you have won a Pyrrhic victory(");
        }
    }

    static void cls(){
        final char esc = 0x1B;

        System.out.printf("%c[1J", esc);
    }
}

abstract class Participant{
    Battlefield field;
    ArrayList<Unit> squad = new ArrayList<Unit>();
    
    abstract void makeTurn();
    abstract void hire();

    void setBattlefield(Battlefield field){
        this.field = field;
    }
    
    boolean stillAlive(){
        return this.squad.size() > 0 ;
    }

}

class Battlefield{
    Terrain[][] regions = new Terrain[15][15];

    Battlefield(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                regions[i][j] = new Terrain();
            }
        }

        this.printFrame();
        this.printField();
    }

    double[][] test(){
        return new double[15][15];
    }

    void printField(){
        final char esc = 0x1B;
        final int   colWidth = 3,
                    shiftX = 7,
                    shiftY = 3;

        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.printf("%c[%d;%dH%c", esc, shiftY + i , shiftX + j * colWidth, regions[i][j].getSymbol());
            }
        }
        System.out.printf("%c[2E", esc);   
    }

    void printFrame(){
        final char esc = 0x1B;
        final int   colWidth = 3,
                    shiftX = 7,
                    shiftY = 3;

        for(int i = 0; i < 15; i++){
            System.out.printf("%c[%d;%dH%d", esc, shiftY -1 , shiftX + i * colWidth, i + 1);
            System.out.printf("%c[%d;%dH%d", esc, shiftY + i , shiftX - colWidth - 1, i + 1);
        }
        System.out.printf("%c[2E", esc);
    }
}

class Enemy extends Participant{

    void makeTurn(){

    }

    void hire(){

    }
}

class City extends Participant{

    void hire(){

    }

    void makeTurn(){

    }
}

abstract class Unit{
    WarriorType type;
    double h, atk, atkRange, shield, moveRange, cost;
    int x, y;

    public double[] atk(){
        return new double[]{atkRange, atk};
    };
    
    public double takeHit(double atk){
        if(shield >= atk){
            shield -= atk;
        }
        else{
            h -= atk - shield;
        }
        return h;
    };
    
    public double getMoveScore(){
        return moveRange;
    };

    public double getCost(){
        return cost;
    }

    public void move(int x, int y){
        this.x = y;
        this.y = y;
    }

    public int[] getPos(){
        return new int[]{x, y};
    }
}

class Infantry extends Unit{
    Infantry(int n){
        switch (n) {
        case 0:
            h=          50;
            atk=        5;
            atkRange=   1;
            shield=     8;
            moveRange=  3;
            cost=       10;
            break;
        case 1:
            h=          35;
            atk=        3;
            atkRange=   1;
            shield=     4;
            moveRange=  6;
            cost=       15;
            break;
        case 2:
            h=          45;
            atk=        9;
            atkRange=   1;
            shield=     3;
            moveRange=  4;
            cost=       20;
            break;
        }
    }
}

class Archer extends Unit{
    Archer(int n){
        switch (n) {
        case 0:
            h=          30;
            atk=        6;
            atkRange=   5;
            shield=     8;
            moveRange=  2;
            cost=       15;
            break;
        case 1:
            h=          25;
            atk=        3;
            atkRange=   3;
            shield=     4;
            moveRange=  4;
            cost=       19;
            break;
        case 2:
            h=          40;
            atk=        7;
            atkRange=   6;
            shield=     3;
            moveRange=  2;
            cost=       23;
            break;
        }
    }
}

class Rider extends Unit{
    Rider(int n){
        switch (n) {
        case 0:
            h=          30;
            atk=        5;
            atkRange=   1;
            shield=     3;
            moveRange=  6;
            cost=       20;
            break;
        case 1:
            h=          50;
            atk=        2;
            atkRange=   1;
            shield=     7;
            moveRange=  5;
            cost=       23;
            break;
        case 2:
            h=          25;
            atk=        3;
            atkRange=   3;
            shield=     2;
            moveRange=  5;
            cost=       25;
            break;
        }
    }

}

class Terrain{

    Map<WarriorType, Double> swamp = new HashMap<>();{
        swamp.put(WarriorType.INFANTRY, 1.5);
        swamp.put(WarriorType.ARCHER, 1.8);
        swamp.put(WarriorType.RIDER, 2.2);
    }
    Map<WarriorType, Double> tree = new HashMap<>();{
        swamp.put(WarriorType.INFANTRY, 1.2);
        swamp.put(WarriorType.ARCHER, 2.2);
        swamp.put(WarriorType.RIDER, 1.5);
    }
    Map<WarriorType, Double> hill = new HashMap<>();{
        swamp.put(WarriorType.INFANTRY, 2.0);
        swamp.put(WarriorType.ARCHER, 2.2);
        swamp.put(WarriorType.RIDER, 1.2);
    }
    Map<WarriorType, Double> simple = new HashMap<>();{
        swamp.put(WarriorType.INFANTRY, 1.0);
        swamp.put(WarriorType.ARCHER, 1.0);
        swamp.put(WarriorType.RIDER, 1.0);
    }

    Map<TerrainType, Map<WarriorType, Double>> penaltiMap = new HashMap<>();{
        penaltiMap.put(TerrainType.SIMPLE, simple);
        penaltiMap.put(TerrainType.TREE, tree);
        penaltiMap.put(TerrainType.HILL, hill);
        penaltiMap.put(TerrainType.SWAMP, swamp);
    }
    Map<TerrainType, Character> symbolMap = new HashMap<>();{
        symbolMap.put(TerrainType.SIMPLE, '.');
        symbolMap.put(TerrainType.SWAMP, '~');
        symbolMap.put(TerrainType.TREE, '|');
        symbolMap.put(TerrainType.HILL, '@');
    }
    
    TerrainType type;
    Unit unit = null;

    Random rnd = new Random();

    Terrain(){
        int p =rnd.nextInt(100);
        if(p < 70) type = TerrainType.SIMPLE;
        else if((p=p-70) < 10) type = TerrainType.TREE;
        else if((p=p-10) < 10) type = TerrainType.HILL;
        else  type = TerrainType.SWAMP;
    }
    
    char getSymbol(){
        return symbolMap.get(this.type);
    };

    double getPenalty(WarriorType wtype){
        return penaltiMap.get(this.type).get(wtype);
    }
} 
