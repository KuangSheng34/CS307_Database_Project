public class SingleReply {
        private int reply_id1;
        private int post_id1;
        private int floor1;
        private String author1;
        private String content1;
        private int star1;
        private Integer upper_floor1;

        public SingleReply(int reply_id, int post_id, int floor, String author, String content, int star, Integer upper_floor) {
            this.reply_id1 = reply_id;
            this.author1 = author;
            this.post_id1 = post_id;
            this.floor1 = floor;
            this.content1 = content;
            this.star1 = star;
            this.upper_floor1 = upper_floor;
        }

        public int getReply_id1() {
            return reply_id1;
        }

        public void setReply_id1(int reply_id1) {
            this.reply_id1 = reply_id1;
        }

        public int getPost_id1() {
            return post_id1;
        }

        public void setPost_id1(int post_id1) {
            this.post_id1 = post_id1;
        }

        public int getFloor1() {
            return floor1;
        }

        public void setFloor1(int floor1) {
            this.floor1 = floor1;
        }

        public String getAuthor1() {
            return author1;
        }

        public void setAuthor1(String author1) {
            this.author1 = author1;
        }

        public String getContent1() {
            return content1;
        }

        public void setContent1(String content1) {
            this.content1 = content1;
        }

        public int getStar1() {
            return star1;
        }

        public void setStar1(int star1) {
            this.star1 = star1;
        }

        public Integer getUpper_floor1() {
            return upper_floor1;
        }

        public void setUpper_floor1(int upper_floor1) {
            this.upper_floor1 = upper_floor1;
        }
}
