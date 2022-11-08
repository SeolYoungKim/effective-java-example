package item16.pack1;

class PackagePrivate {
    public String str1;
    public String str2;

    private static class PrivateStatic {
        public Integer int1;
        public Integer int2;
    }

    private class Private {
        public Double db1;
        public Double db2;

        void print() {
            System.out.println(str1);
            System.out.println(str2);
        }
    }

    public static void main(String[] args) {
        System.out.println("hi");
//        System.out.println(int1);
//        System.out.println(int2);
//
//        System.out.println(db1);
//        System.out.println(db2);
    }
}
