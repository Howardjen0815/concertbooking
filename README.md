# concertbooking

# Ticket Management Engine

## Overview

The **Ticket Management Engine** is a robust Java-based application designed to streamline the management of concert ticket bookings. It caters to both administrators and customers by providing distinct operational modes tailored to their specific needs. Administrators can efficiently manage concert details, customer information, and bookings, while customers enjoy a seamless experience when searching for concerts and booking tickets.

## Features

### Dual Operational Modes

- **Admin Mode**: Enables administrators to oversee and manage all aspects of the ticketing system, including adding or modifying concerts, managing customer data, and handling bookings.
- **Customer Mode**: Allows customers to browse available concerts, view detailed information, and securely book tickets.

### Dynamic File Handling

The application reads and processes data from multiple CSV and TXT files, ensuring flexibility and adaptability. File names are not hardcoded but are provided through command-line arguments, allowing for easy updates and scalability.

### Comprehensive Venue Management

Supports various venue layouts with a default fallback option. Specific venue files can be added or omitted as needed, ensuring the system can accommodate different event spaces without issues.

### Robust Booking System

Handles multiple bookings per concert, maintaining detailed records of each ticket, including seat assignments and pricing at the time of booking. This ensures accuracy and accountability in ticket sales.

### Data Integrity and Persistence

Ensures that all changes made during a session are accurately saved back to their respective files. The system employs buffering techniques to prevent data loss, ensuring reliability even in cases of unexpected interruptions.

### User Authentication

In Customer Mode, users can optionally provide their customer ID and password to securely access their booking information and make transactions.

## Command-Line Interface

The application is operated via command-line arguments, offering flexibility in how it is launched and configured. Users can specify various parameters, including the mode of operation (admin or customer), customer credentials, and the paths to necessary data files. This design allows for easy integration with different environments and simplifies the process of updating data sources.

### Modes of Operation

#### Admin Mode

- **Description**: Launches the application with administrative privileges.
- **Requirements**:
  - Specifies paths to customer, concert, and booking data files.
  - Includes any specific venue files as needed.

#### Customer Mode

- **Description**: Launches the application for customer interactions.
- **Features**:
  - Optionally accepts customer ID and password for authenticated access.
  - Requires specifying paths to customer, concert, and booking data files.
  - Includes any specific venue files as needed.

## File Structure and Data Management

The application interacts with several key data files, each serving a distinct purpose:

- **Customer File (`customer.csv`)**: Contains records of all customers, including their unique IDs, names, and passwords.
- **Concert File (`concert.csv`)**: Stores detailed information about each concert, such as concert ID, date, timing, artist name, venue, and pricing details for different seating zones.
- **Booking File (`bookings.csv`)**: Maintains records of all bookings made by customers, detailing the booking ID, customer ID, concert ID, number of tickets, and specific ticket information like seat assignments and prices at the time of booking.
- **Venue Files (`venue_default.txt`, `venue_mcg.txt`, `venue_marvel.txt`, etc.)**: Define the layout of various venues, specifying zones (VIP, SEATING, STANDING) and the arrangement of seats or spots within each zone.

The application intelligently selects the appropriate venue file based on the concert's venue name. If a specific venue file is unavailable, it defaults to using the `venue_default.txt` to ensure seamless operation.

## Data Persistence

Upon exiting, the application ensures that all changes made during the session—such as new bookings, updated concert details, or modified customer information—are accurately written back to their respective files. This is achieved by employing buffering techniques and flushing data streams to prevent data loss and ensure consistency.

**Important:** Do **not** override venue files during the save process.

## Error Handling

- **Invalid Mode Selection**: If an unrecognized mode is specified (neither `--admin` nor `--customer`), the application will notify the user with an error message and terminate safely.
- **File Availability**: The system ensures that essential files like `venue_default.txt` are always available. For other venue-specific files, it gracefully defaults to the standard layout if specific files are missing.
- **Command-Line Argument Validation**: The application meticulously checks the provided command-line arguments, handling optional and variable-length parameters to prevent runtime errors and ensure smooth operation.
- 
## Notes

- **ID Management**: All IDs (`customerId`, `concertId`, `bookingId`) start from `1`. Adjust indexing when using zero-based data structures.
- **No Headers**: Data files do not contain headers. Ensure that the program reads data accordingly without expecting header rows.
- **Venue Files**: The application is flexible to handle varying numbers of venue files. Ensure that the default venue file path (`assets/venue_default.txt`) is correctly set within the code.

##Command line
- **java TicketManagementEngine --admin assets/customer.csv assets/concert.csv assets/bookings.csv assets/venue_mcg.txt assets/venue_marvel.txt        
