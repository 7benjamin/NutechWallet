package com.example.nutechwallet.model;


import java.util.List;

public class HistoryTransJson {
    public class RootCallback{
        public Integer status;
        public String message;
        public List<Data> data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public class Data{
            public String transaction_id;
            public String transaction_time;
            public String transaction_type;
            public String amount;

            public String getTransaction_id() {
                return transaction_id;
            }

            public void setTransaction_id(String transaction_id) {
                this.transaction_id = transaction_id;
            }

            public String getTransaction_time() {
                return transaction_time;
            }

            public void setTransaction_time(String transaction_time) {
                this.transaction_time = transaction_time;
            }

            public String getTransaction_type() {
                return transaction_type;
            }

            public void setTransaction_type(String transaction_type) {
                this.transaction_type = transaction_type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }
    }
}

