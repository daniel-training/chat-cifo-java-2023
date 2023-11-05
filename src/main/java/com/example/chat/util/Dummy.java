package com.example.chat.util;

import com.example.chat.model.Room;
import com.github.javafaker.Faker;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Utility Class that facilities generation of dummy data. This Class can not be inherited <code>final</code> and can
 * not be instantiated, all methods must be <code>static</code>.
 */
public final class Dummy {

    // Avoids be instantiated
    private Dummy() {
    }


    /**
     * Generates a random timestamp between two dates.
     * <p>
     * Start and end ranges are dates, time limits are added internally. Time is in UTC.
     *
     * @param startInclusive start date of bounds to generate the random the timestamp
     * @param endExclusive   end date of bounds to generate the random the timestamp
     * @return a random timestamp between provided range
     */
    public static Instant randomTimestampBetween(LocalDate startInclusive, LocalDate endExclusive) {

        //// Add time and time zone to date ranges and convert to instant from Epoch seconds
        //Instant startInstant = Instant.ofEpochSecond(startInclusive.toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC));
        //Instant endInstant = Instant.ofEpochSecond(endExclusive.toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC));

        // Add time and time zone to date ranges and convert to Instant
        Instant startInstant = Instant.from(ZonedDateTime.of(startInclusive, LocalTime.of(0, 0, 0), ZoneOffset.UTC));
        Instant endInstant = Instant.from(ZonedDateTime.of(endExclusive, LocalTime.of(0, 0, 0), ZoneOffset.UTC));

        return randomTimestampBetween(startInstant, endInstant);
    }

    /**
     * Generates a random timestamp between two timestamps.
     * <p>
     * Start and end ranges are timestamps of type Instant. Complementary exists an overloading method to generate the
     * random timestamp from a range of dates where time and time zone are added internally.
     *
     * @param startInclusive start timestamp of bounds to generate the random the timestamp
     * @param endExclusive   end timestamp of bounds to generate the random the timestamp
     * @return a random timestamp between the provided range
     */
    public static Instant randomTimestampBetween(Instant startInclusive, Instant endExclusive) {
        // Add time to date ranges and convert to Epoch seconds
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        // Random Epoch seconds
        long randomSeconds = new Random().nextLong((endSeconds - startSeconds) + 1) + startSeconds;

        return Instant.ofEpochSecond(randomSeconds);
    }

    /**
     * Generates a random timestamp between a given initial timestamp and a later bound date provided by maximum
     * additional days.
     *
     * @param timestampInclusive timestamp that indicates the start of the range from which to generate a later
     *                           timestamp
     * @param boundDays          number of days to set the end timestamp of the range
     * @return a random timestamp later to the initial given timestamp
     */
    public static Instant randomTimestampAfter(Instant timestampInclusive, long boundDays) {
        Instant endTimestampExclusive = timestampInclusive.plus(boundDays, ChronoUnit.DAYS);
        return randomTimestampBetween(timestampInclusive, endTimestampExclusive);
    }

    /**
     * Generates a random timestamp later than a given timestamp
     *
     * @param timestampInclusive timestamp that indicates the start of the range from which to generate a later
     *                           timestamp
     * @return a random timestamp later to the initial given timestamp
     */
    public static Instant randomTimeStampAfter(Instant timestampInclusive) {
        return randomTimestampBetween(timestampInclusive, Instant.MAX);
    }

    /**
     * Generates a random timestamp between a given initial timestamp and a previous limit timestamp provided by maximum
     * previous days
     *
     * @param timestampExclusive timestamp that indicates from which to generate a previous timestamp
     * @param boundDays          number of days to set the maximum upper timestamp of the range
     * @return a random timestamp prior to the initial given timestamp
     */
    public static Instant randomTimeStampBefore(Instant timestampExclusive, long boundDays) {
        Instant startTimestampInclusive = timestampExclusive.minus(boundDays, ChronoUnit.DAYS);
        return randomTimestampBetween(startTimestampInclusive, timestampExclusive);
    }

    /**
     * Generates a random date between a given initial date and a previous limit date provided by maximum previous days
     * Generates a random date after a given date
     *
     * @param timestampExclusive timestamp from which to generate a previous timestamp
     * @return a random timestamp prior to the initial given timestamp
     */
    public static Instant randomTimeStampBefore(Instant timestampExclusive) {
        return randomTimestampBetween(Instant.MIN, timestampExclusive);
    }

    /**
     * Generate a dummy Room
     *
     * @return a dummy Room
     */
    public static Room generateRoom() {

        Faker dummy = new Faker(new Locale("en-US"));

        Instant createdAt = randomTimestampBetween(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        return new Room(UUID.randomUUID().toString(),
                dummy.job().field(),
                dummy.lorem().sentence(10, 4),
                null, //TODO set a random owner. Currently basic version only system rooms are allowed
                createdAt,
                randomTimestampAfter(createdAt, 60)
        );
    }


    /**
     * Generate a collection of dummy rooms.
     * <p>
     * The title of the collection can be unique or not. If non unique title is set, the number of elements of the
     * collection corresponds to the requested number parameter, but if unique title is set, then the total number of
     * elements are not guarantee, in this case always collection elements <= number rooms requested due randomness of
     * collisions for unique titles creation. This is preferable than blocking the process waiting to obtain all rooms
     * with uniquely title, whatever the amount number required. And also having to rely on a sufficient set of source
     * titles to draw from randomly.
     *
     * @param number      amount of rooms to be generate. if unique title is not required: generated rooms == number,
     *                    else generated rooms <= number
     * @param uniqueTitle sets for require unique titles in the rooms collection
     * @return collection of Rooms
     */
    public static List<Room> generateRooms(int number, boolean uniqueTitle) {

        List<Room> rooms = new ArrayList<>();

        if (uniqueTitle) {
            // unique titles collection
            HashSet<String> titles = new HashSet<String>();
            Faker dummy = new Faker(new Locale("en-US"));

            // Generate a collection of unique titles. HashSet add method ensures for that, it returns true/false if can
            // or not perform the operation.
            for (int i = 0; i < number; i++) {
                titles.add(dummy.job().field());
            }

            // Per each title generate a room and override with the current title of the collection.
            for (String title : titles){
                Room room = new Room();
                room = generateRoom();
                room.setTitle(title);
                rooms.add(room);
            }
        } else {
            // Generate rooms, titles can non unique and cam be repeated.
            for (int i = 0; i < number; i++) {
                rooms.add(generateRoom());
            }
        }

        return rooms;
    }

}
