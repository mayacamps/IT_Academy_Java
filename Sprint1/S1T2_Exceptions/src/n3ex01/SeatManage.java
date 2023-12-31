package n3ex01;

import n3ex01.exceptions.FreeSeatException;
import n3ex01.exceptions.OccupiedSeatException;

import java.util.ArrayList;

public class SeatManage {
    private ArrayList<Seat> seats;

    public SeatManage(){
        this.seats = new ArrayList<Seat>();
    }

    public ArrayList<Seat> getSeats() {
        return this.seats;
    }

    public void addSeat(Seat newSeat)  {
       if (findSeat(newSeat.getRowNum(), newSeat.getSeatNum()) == -1){
           getSeats().add(newSeat);
           System.out.println("Seat reserved!\n");
       } else {
           System.out.println("Error. Seat is already reserved.\n");
       }
    }

    public int findSeat(int numRow, int numSeat) {
        for (Seat s: seats){
            if (s.getSeatNum() == numSeat && s.getRowNum() == numRow){
                return seats.indexOf(s);
            }
        }
        return -1;
    }

    public void deleteSeat(int numRow, int numSeat) throws FreeSeatException {
        int pos = findSeat(numRow, numSeat);
        if (pos == -1){
            throw new FreeSeatException();
        }
        seats.remove(pos);
    }
}
