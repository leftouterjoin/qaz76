package javaeval;

import java.io.File;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class JavaevalServlet extends HttpServlet {
	@Override
	public void init() {
		ClassPool cp = ClassPool.getDefault();
		try {
			String rp = getServletContext().getRealPath("WEB-INF/classes");
			cp.appendClassPath(rp);
			for (File f : (new File(rp).listFiles())) {
				String pathname = f.getAbsolutePath();
				if (pathname.endsWith(".jar")) {
					cp.appendClassPath(pathname);
				}
			}
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		ClassPool cp = ClassPool.getDefault();
		try {
			CtClass cc = null;
			try {
				cc = cp.get("javaeval.EvalerImpl");
				cc.defrost();
			} catch(NotFoundException e) {
				cc = cp.makeClass("javaeval.EvalerImpl");
			}
			CtMethod cm = CtNewMethod
					.make("public void eval(){System.out.println(\"Hello world\");java.util.regex.Matcher m;}",
							cc);
			try {
				cc.removeMethod(cm);
			} catch(NotFoundException e) {
				// NOOP.
			}
			cc.addMethod(cm);
			cc.addInterface(cp.get(Evaler.class.getName()));
			Evaler evaler = (Evaler) cc.toClass().newInstance();
			cc.defrost();
			cc.detach();
			evaler.eval();
			resp.getWriter().println(evaler);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}

		resp.getWriter().println("Hello, world");

		java.util.regex.Matcher m = java.util.regex.Pattern.compile(
				"(hoge)|(hogehoge)").matcher("hoge");
		if (m.matches())
			resp.getWriter().println(m.group(0));
	}

	interface Evaler {
		public void eval();
	}
}
