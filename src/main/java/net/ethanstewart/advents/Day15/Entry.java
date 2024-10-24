package net.ethanstewart.advents.Day15;

record Entry(String label, int focalPower) {

    public static Entry parseEntry(String entryString) {
        String[] parts = entryString.split("=");
        return new Entry(parts[0], Integer.parseInt(parts[1]));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entry && this.label.equals(((Entry) obj).label);
    }
}
