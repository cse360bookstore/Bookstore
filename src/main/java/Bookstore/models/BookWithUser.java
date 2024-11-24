package Bookstore.models;

public class BookWithUser {
        private int bookID;
        private String title;
        private String category;
        private String bookCondition;
        private String author;
        private int publishYear;
        private double price;
        private String listedAt;
        private String listedByUsername;
        private String listedByEmail;
        private String listedByFirstName;
        private String listedByLastName;

        public BookWithUser(int bookID, String title, String category, String bookCondition, String author,
                    int publishYear, double price, String listedAt, String listedByUsername,
                    String listedByEmail, String listedByFirstName, String listedByLastName) {
            this.bookID = bookID;
            this.title = title;
            this.category = category;
            this.bookCondition = bookCondition;
            this.author = author;
            this.publishYear = publishYear;
            this.price = price;
            this.listedAt = listedAt;
            this.listedByUsername = listedByUsername;
            this.listedByEmail = listedByEmail;
            this.listedByFirstName = listedByFirstName;
            this.listedByLastName = listedByLastName;
        }

        public int getBookID() {
            return bookID;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public String getBookCondition() {
            return bookCondition;
        }

        public String getAuthor() {
            return author;
        }


        public double getPrice() {
            return price;
        }

        public String getListedAt() {
            return listedAt;
        }

        public String getListedByUsername() {
            return listedByUsername;
        }

        public String getListedByEmail() {
            return listedByEmail;
        }

        public String getListedByFirstName() {
            return listedByFirstName;
        }

        public String getListedByLastName() {
            return listedByLastName;
        }


        @Override
        public String toString() {
            return "BookWithUser{" +
                    "bookID=" + bookID +
                    ", title='" + title + '\'' +
                    ", category='" + category + '\'' +
                    ", bookCondition='" + bookCondition + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    ", listedAt='" + listedAt + '\'' +
                    ", listedByUsername='" + listedByUsername + '\'' +
                    '}';
        }
    }

