package kr1v.malilibApi.widget;

public class Util {
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> void rethrow(Throwable t) throws T {
		throw (T)t;
	}
}
