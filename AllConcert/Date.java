package AllConcert;

/**
 * The Date class store the information of date and time.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Date {

    /**
     * The Date and time of the concert
     */
    private String concertDate;
    private String concertTime;

    /**
     * Construct the date and time
     * @param concertDate the date of concert
     * @param concertTime the time of the concert
     */
    public Date(String concertDate, String concertTime) {
        //construct the date and time
        setConcertDate(concertDate);
        setConcertTime(concertTime);
    }

    /**
     * get the concertDate
     * @return concertDate
     */
    public String getConcertDate() {
        return concertDate;
    }

    /**
     * set the concertDate
     * @param concertDate the Date we hope to set
     */
    public void setConcertDate(String concertDate) {
        this.concertDate = concertDate;
    }

    /**
     * get the concer time
     * @return concert time
     */
    public String getConcertTime() {
        return concertTime;
    }

    /**
     * set the concert time
     * @param concertTime the time we hope to set
     */
    public void setConcertTime(String concertTime) {
        this.concertTime = concertTime;
    }
}
