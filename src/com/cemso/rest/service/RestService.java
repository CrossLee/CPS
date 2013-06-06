package com.cemso.rest.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.jna.JoymindCommDLL.JoymindCommDLLLib;
import com.cemso.util.FetchData;

@Path("/root")
public class RestService {

	private static final Log log = LogFactory.getLog(RestService.class);
	JoymindCommDLLLib instance = JoymindCommDLLLib.INSTANCE;
	
	@POST
	@Path("/checkIP")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkIP(@FormParam(value = "ip") String ip) {
		if (log.isDebugEnabled()) {
			log.debug("RESTFUL CALL FOR CHECK DEVICE IP...");
		}
		String isExist = "false";

		String sql = "select * from device where ip = '"+ip+"';";
		List<Object> list = FetchData.queryObject(sql, "com.cemso.dto.DeviceDTO");
		if(list != null && !list.isEmpty()){
			isExist = "true";
			return isExist;
		}
		
		instance.ResetDLL();
		int flag = instance.AdjustTime(ip);
		if(log.isInfoEnabled()){
			log.info("instance.AdjustTime("+ip+"): "+flag);
		}
		if(flag == 0){
			isExist = "offline";
		}else{
			isExist = "online";
		}
		
		return isExist;
	}
}