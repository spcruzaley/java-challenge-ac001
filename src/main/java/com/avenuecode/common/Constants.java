package com.avenuecode.common;

public class Constants {

    private Constants() {
    }
    
    public static final String PAYLOAD_TEST = "{" +
            "  \"data\":[" +
            "    { " +
            "      \"source\": \"A\", \"target\": \"B\", \"distance\":5" +
            "    }," +
            "    { " +
            "      \"source\": \"B\", \"target\": \"C\", \"distance\":4" +
            "    }," +
            "    { " +
            "      \"source\": \"C\", \"target\": \"D\", \"distance\":8" +
            "    }," +
            "    { " +
            "      \"source\": \"D\", \"target\": \"C\", \"distance\":8" +
            "    }," +
            "    { " +
            "      \"source\": \"D\", \"target\": \"E\", \"distance\":6" +
            "    }," +
            "    { " +
            "      \"source\": \"A\", \"target\": \"D\", \"distance\":5" +
            "    }," +
            "    { " +
            "      \"source\": \"C\", \"target\": \"E\", \"distance\":2" +
            "    }," +
            "    { " +
            "      \"source\": \"E\", \"target\": \"B\", \"distance\":3" +
            "    }," +
            "    { " +
            "      \"source\": \"A\", \"target\": \"E\", \"distance\":7" +
            "    }" +
            "  ]" +
            "}";
}
