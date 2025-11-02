import java.util.ArrayList;
import java.util.List;

import figures.Bishop;
import figures.Color;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Piece;
import figures.Queen;
import figures.Rook;

public class Board {
    private final Piece[][] b = new Piece[8][8];
    private Move lastMove = null;
    private final List<String> history = new ArrayList<>();

    public Board() { setup(); recordBoardState(); }

    public void setup() {
        for(int r=0;r<8;r++) for(int c=0;c<8;c++) b[r][c]=null;

        for(int c=0;c<8;c++){
            b[6][c]=new Pawn(Color.WHITE);
            b[1][c]=new Pawn(Color.BLACK);
        }

        b[7][0]=new Rook(Color.WHITE); b[7][7]=new Rook(Color.WHITE);
        b[0][0]=new Rook(Color.BLACK); b[0][7]=new Rook(Color.BLACK);

        b[7][1]=new Knight(Color.WHITE); b[7][6]=new Knight(Color.WHITE);
        b[0][1]=new Knight(Color.BLACK); b[0][6]=new Knight(Color.BLACK);

        b[7][2]=new Bishop(Color.WHITE); b[7][5]=new Bishop(Color.WHITE);
        b[0][2]=new Bishop(Color.BLACK); b[0][5]=new Bishop(Color.BLACK);

        b[7][3]=new Queen(Color.WHITE); b[0][3]=new Queen(Color.BLACK);
        b[7][4]=new King(Color.WHITE); b[0][4]=new King(Color.BLACK);
    }

    Piece get(int r,int c){ return (r<0||r>7||c<0||c>7)?null:b[r][c]; }
    void set(int r,int c, Piece p){ if(r>=0 && r<8 && c>=0 && c<8) b[r][c]=p; }

    public void setLastMove(Move m) {
        this.lastMove = m;
    }

    public Move getLastMove() {
        return this.lastMove;
    }

    public boolean isClearPath(Move m){
        int dr=Integer.signum(m.toR-m.fromR);
        int dc=Integer.signum(m.toC-m.fromC);

        int r=m.fromR+dr, c=m.fromC+dc;

        while(r!=m.toR || c!=m.toC){
            if(get(r,c)!=null) return false;
            r+=dr; c+=dc;
        }
        return true;
    }

    public boolean isAttacked(int r, int c, Color side){
        Color enemy = (side == Color.WHITE ? Color.BLACK : Color.WHITE);
        for(int fr=0;fr<8;fr++) for(int fc=0;fc<8;fc++){
            Piece p=get(fr,fc);
            if(p==null || p.color!=enemy) continue;
            Move m=new Move(fr,fc,r,c,null);

            if (p.getType() == Type.PAWN) {
                int dir = (enemy == Color.WHITE ? -1 : 1);
                if (Math.abs(m.toC - m.fromC) != 1 || m.toR - m.fromR != dir) continue;
            }

            if(p.isValidMove(this,m)) return true;
        }
        return false;
    }

    public boolean isLegalMove(Move m, Color side){
        Piece p=get(m.fromR,m.fromC);
        if(p==null || p.color!=side) return false;
        Piece target=get(m.toR,m.toC);
        if(target!=null && target.color==side) return false;

        if(!p.isValidMove(this,m)) return false;

        Board copy=this.copy();
        copy.makeMove(m);
        return !copy.isInCheck(side);
    }

    public void makeMove(Move m){
        Piece p = get(m.fromR,m.fromC);
        if(p==null) return;

        set(m.toR,m.toC,p);
        set(m.fromR,m.fromC,null);

        p.hasMoved = true;
        setLastMove(m);
        recordBoardState();
    }

    public Piece getLastMovedPawn() {
        if (lastMove == null) return null;
        Piece p = get(getLastMove().toR, getLastMove().toC);
        if (p != null && p.getType()==Type.PAWN && Math.abs(getLastMove().toR-getLastMove().fromR)==2)
            return p;
        return null;
    }

    public boolean isInCheck(Color side){
        int kr=-1,kc=-1;
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=get(r,c);
            if(p!=null && p.getType()==Type.KING && p.color==side){ kr=r; kc=c; }
        }
        if(kr==-1) return true;

        Color enemy = (side==Color.WHITE?Color.BLACK:Color.WHITE);
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=get(r,c);
            if(p==null || p.color!=enemy) continue;
            Move m=new Move(r,c,kr,kc,null);
            if(p.isValidMove(this,m)) return true;
        }
        return false;
    }

    public boolean hasLegalMoves(Color side){
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=get(r,c);
            if(p==null || p.color!=side) continue;
            for(int tr=0;tr<8;tr++) for(int tc=0;tc<8;tc++){
                Move m=new Move(r,c,tr,tc,null);
                if(isLegalMove(m,side)) return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(Color side){
        return isInCheck(side) && !hasLegalMoves(side);
    }

    public void print(){
        System.out.println("    a b c d e f g h");
        System.out.println("  +------------------------+");
        for(int r=0;r<8;r++){
            System.out.print((8-r)+" | ");
            for(int c=0;c<8;c++){
                Piece p=b[r][c];
                System.out.print((p==null?".":p)+" ");
            }
            System.out.println("|");
        }
        System.out.println("  +------------------------+");
        System.out.println("    a b c d e f g h");
    }

    public Board copy(){
        Board nb=new Board();
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=get(r,c);
            if(p!=null){
                Piece newP;
                switch(p.getType()){
                    case KING: newP=new King(p.color); break;
                    case QUEEN: newP=new Queen(p.color); break;
                    case ROOK: newP=new Rook(p.color); break;
                    case BISHOP: newP=new Bishop(p.color); break;
                    case KNIGHT: newP=new Knight(p.color); break;
                    case PAWN: newP=new Pawn(p.color); break;
                    default: newP = null;
                }
                if (newP != null) {
                    newP.hasMoved = p.hasMoved;
                    nb.b[r][c] = newP;
                }
            } else nb.b[r][c]=null;
        }
        nb.lastMove = this.lastMove;
        nb.history.addAll(this.history);
        return nb;
    }

    public void recordBoardState(){
        StringBuilder sb=new StringBuilder();
        for(int r=0;r<8;r++) for(int c=0;c<8;c++){
            Piece p=get(r,c);
            sb.append(p==null?".":p.toString());
        }
        history.add(sb.toString());
    }

    public boolean isThreefoldRepetition(){
        if(history.isEmpty()) return false;
        String last = history.get(history.size()-1);
        long count = history.stream().filter(s->s.equals(last)).count();
        return count>=3;
    }
}