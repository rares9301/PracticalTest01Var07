package ro.pub.cs.systems.eim.practicaltest01var07;

public final class Constants {
    private Constants() { }

    public static final String TAG = "PracticalTest01Var07";

    // main -> secondary (B.2)
    public static final String EXTRA_VALUES = "ro.pub.cs.systems.eim.practicaltest01var07.VALUES";
    // secondary -> main (C.1)
    public static final String EXTRA_RESULT = "ro.pub.cs.systems.eim.practicaltest01var07.RESULT";
    public static final String EXTRA_OPERATION = "ro.pub.cs.systems.eim.practicaltest01var07.OPERATION";
    public static final int SECONDARY_REQUEST_CODE = 1;

    // saved instance state keys (C.2)
    public static final String STATE_HAS_RESULT = "has_result";
    public static final String STATE_RESULT = "result";

    // service broadcast (D.1 / D.2)
    public static final String ACTION_RANDOM_VALUES = "ro.pub.cs.systems.eim.practicaltest01var07.RANDOM_VALUES";
    public static final String EXTRA_RANDOM_INTS = "ro.pub.cs.systems.eim.practicaltest01var07.RANDOM_INTS";
    public static final long SERVICE_PERIOD_MS = 10_000L;
}
