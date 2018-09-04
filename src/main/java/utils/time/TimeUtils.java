package utils.time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

* 创建时间：2018年8月14日 下午6:12:53  

* 项目名称：okHttp  

* @author lizhen  

* @version 1.0   

* 类说明：  

*/
public class TimeUtils {
	//根据时间间隔进行取舍时间获取年月日时分的字符串(定位轨迹过滤抽样数据)
	public static String timeSpan(Date time, int timeSpan){
		StringBuilder timeStr = new StringBuilder();
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(time);
	    //获取年
	    int year = calendar.get(calendar.YEAR);
	    timeStr.append(year + "-");
	    //获取月
	    int month = calendar.get(calendar.MONTH) + 1;
	    timeStr.append(month + "-");
	    //获取日
	    int day =  calendar.get(calendar.DATE);
	    timeStr.append(day + "-");
	    //获取小时
	    int hour = calendar.get(calendar.HOUR_OF_DAY);
	    timeStr.append(hour + "-");
	    //获取分钟
	    int minute = calendar.get(calendar.MINUTE);
	    //时间抽样
	    int m = ( minute / timeSpan ) * timeSpan;
	    timeStr.append(m);
	    
	    return timeStr.toString();
	}
	
	public static void main(String[] args) {
		List<user> u = new ArrayList<user>();
		long t = 1531218306l;
		for(int i = 1 ; i < 100 ; i++){
			user uu = new user();
			uu.setName("name"+i);
			uu.setAge(i);
			uu.setTime(new Date((t+(i * 60)) * 1000));
			u.add(uu);
		}
		System.out.println(u);
		Map<String, user> m = new HashMap<>();
		List<user> u2 = new ArrayList<user>();
		for (user user : u) {
			String key = timeSpan(user.getTime(),2);
			if(!m.containsKey(key)){
				m.put(key, user);
				u2.add(user);
			}
		}
		System.out.println(u2);
	}
	
}

class user {
	String name;
	int age;
	Date time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "user [name=" + name + ", age=" + age + ", time=" + getFormatedDateTime(time.getTime()) + "]";
	}
	
	public String getFormatedDateTime(Long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return formatter.format(new Date(time));
	}
}
