package AllConcert;

import AllConcert.Venue;

/**
 * The Concert class store each concert information, include artist name, venue, date, price, place.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Concert {

    /**
     * Construct the concert
     * @param venue        the place of concert
     * @param date         the date of concert
     * @param artistName   the artist of concert
     * @param place        the city of concert
     * @param price     the price of concert
     */
    public Concert(AllConcert.Venue venue, Date date, String artistName, String place, Price price) {
        this.venue = venue;
        setDate(date);
        setPrice(price);
        this.artistName = artistName;
        this.place = place;
        this.bookedSeat = 0;
    }

    /** The concerts have the venue, date, time, artistName, place, remainSeats, leftPrice, middlePrice, rightPrice and how many seats have been booked, and remained seat*/
    // create some instance variables here that represents a concert
    private Venue venue;//each concert will have the venue to store the layout and booked ticket
    private Date date;
    private String artistName;
    private String place;
    private Price price;
    private int bookedSeat;

    /**
     * get the concert venue
     * @return venue of concert
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * get the artist name
     * @return artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * get the concert place
     * @return place
     */
    public String getPlace() {
        return place;
    }

    /**
     * get the total booked seat
     * @return bookedSeat
     */
    public int getBookedSeat() {
        return bookedSeat;
    }

    /**
     * set the booked seat
     * @param bookedSeat the booking seat
     */
    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }


    /**
     * get the detail Datetime of concert
     * @return the date of concert
     */
    public Date getDate() {
        return date;
    }

    /**
     * set the date
     * @param date the date of concert
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**\
     * get the price
     * @return the price of concert
     */
    public Price getPrice() {
        return price;
    }

    /**
     * set the price
     * @param price concert price
     */
    public void setPrice(Price price) {
        this.price = price;
    }
}
