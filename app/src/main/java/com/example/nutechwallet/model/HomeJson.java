package com.example.nutechwallet.model;


public class HomeJson {
    public class Root{
        public Request request;

        public void setRequest(Request request) {
            this.request = request;
        }

        public class Request {
            private String lang;
            public Data data;

            public void setLang(String lang) {
                this.lang = lang;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public Data getData() {
                return data;
            }

            public class Data {
                public String username;


                public void setUsername(String username) {
                    this.username = username;
                }

            }
        }


    /*        public void setPn(String pn) {
            this.pn = pn;
        }*/
    }

    public class RootCallback{
        public Callback callback;

        public Callback getCallback() {
            return callback;
        }

        public class Callback {
            public String responseStatus;
            public String responseMessage;
            public Data data;

            public Data getData() {
                return data;
            }

            public String getResponseStatus() {
                return responseStatus;
            }

            public void setResponseStatus(String responseStatus) {
                this.responseStatus = responseStatus;
            }

            public String getResponseMessage() {
                return responseMessage;
            }

            public void setResponseMessage(String responseMessage) {
                this.responseMessage = responseMessage;
            }

            public class Data{
                public String nik;
                public boolean hasProfilePict;
                public String jabatan;
                public String fullName;
                public String noHp;
                public String locationName;
                public String total;
                public String organization;


                public String getNik() {
                    return nik;
                }

                public boolean isHasProfilePict() {
                    return hasProfilePict;
                }

                public String getJabatan() {
                    return jabatan;
                }

                public String getFullName() {
                    return fullName;
                }

                public String getLocationName() {
                    return locationName;
                }

                public String getNoHp() {
                    return noHp;
                }

                public String getTotal() {
                    return total;
                }

                public String getOrganization() {
                    return organization;
                }
            }
        }
    }
}

