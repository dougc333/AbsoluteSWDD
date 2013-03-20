
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static ServletConfig servletConfig;
    private static Jedis jedis;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
        try{
        jedis = new Jedis("localhost"); //replace w/storm0 or local ip when nto at starbucks
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		servletConfig = config;
		servletConfig.getServletContext().log("TestServlet init method");
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		Enumeration<String> pNames = request.getParameterNames();
		while(pNames.hasMoreElements()){
			String n = pNames.nextElement();
			System.out.println("parameter name:"+n+" value:"+request.getParameter(n));
			//response.getWriter().write("this is a test");
		}
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("POST");
		writeDDRequestToRedis(request);
	}

	private void writeDDRequestToRedis(HttpServletRequest  request){
		Enumeration<String> pNames = request.getParameterNames();
		Map<String,String> m = new TreeMap<String,String>();
		while(pNames.hasMoreElements()){
			String n = pNames.nextElement();
			System.out.println("parameter name:"+n+" value:"+request.getParameter(n));
			//response.getWriter().write("this is a test");		
			m.put(n,request.getParameter(n));
		}
		jedis.hmset("1000", m);
		HashMap<String, String> hash = new HashMap<String,String>(){
			{
			put("a","aaaa");
			put("b","bbbb");
			}
		};
		jedis.hmset("key", hash);
		jedis.hset("testkey", "key", "value");
	}
	
	
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
