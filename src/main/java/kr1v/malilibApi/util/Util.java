package kr1v.malilibApi.util;

public class Util {
	/// usage: try { ... } catch( ... e) { return rethrow(e); }
	@SuppressWarnings("unchecked")
	public static <T extends Throwable, R> R rethrow(Throwable t) throws T {
		throw (T)t;
	}
}
