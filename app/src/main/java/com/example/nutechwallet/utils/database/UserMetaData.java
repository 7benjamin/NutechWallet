package com.example.nutechwallet.utils.database;


    public class UserMetaData {

        private Integer sqliteId;
        private String email;
        private String firstName;
        private String lastName;
        private String token;


        public Integer getSqliteId() {
            return sqliteId;
        }

        public void setSqliteId(Integer sqliteId) {
            this.sqliteId = sqliteId;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
