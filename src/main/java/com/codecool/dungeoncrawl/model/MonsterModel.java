package com.codecool.dungeoncrawl.model;

public class MonsterModel extends BaseModel {
        private String tileName;
        private int x;
        private int y;

        public MonsterModel(String tileName, int x, int y) {
            this.tileName = tileName;
            this.x = x;
            this.y = y;

        }


        public String getTileName() {
            return tileName;
        }


        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

}
