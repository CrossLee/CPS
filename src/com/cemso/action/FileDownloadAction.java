package com.cemso.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipOutputStream;
import org.jdom.JDOMException;

import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.dto.TemplateDTO;
import com.cemso.service.PlaylistService;
import com.cemso.service.ProgramService;
import com.cemso.service.TemplateService;
import com.cemso.util.FileOperationTool;
import com.cemso.util.XmlOperationTool;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	// zip path
	private String path = "";
	private String fileName;
	
	private static final Log log = LogFactory.getLog(com.cemso.action.FileDownloadAction.class);
	
	private PlaylistService playlistService;
	private TemplateService templateService;
	private ProgramService programService;
	
	public PlaylistService getPlaylistService() {
		return playlistService;
	}
	public void setPlaylistService(PlaylistService playlistService) {
		this.playlistService = playlistService;
	}
	public TemplateService getTemplateService() {
		return templateService;
	}
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	public ProgramService getProgramService() {
		return programService;
	}
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	public InputStream getDownloadFile() {
	    try {
			if(log.isDebugEnabled()){
			    log.debug("Download rar file path:"+path);
			}
			InputStream is = null;
			File file = new File(path);
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {
			    if(log.isErrorEnabled()){
			    	log.error("can not find download file:"+path);
			        log.error(e.getMessage());
			    }
			}
			return is;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String execute() {
		try {
		    if(log.isDebugEnabled()){
	            log.debug("start to get the details about this playlist...");
	        }
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
			String id = request.getParameter("id");
			String type = id.split("_")[0];

			if (type.equals(XmlOperationTool.DB_TYPE_PLAYLIST)) {
				PlaylistDTO downloadPlaylistDTO = null;
				List<PlaylistDTO> playlistList = playlistService.getPlaylist();

				// query playlist for download
				for (PlaylistDTO playlist : playlistList) {
					if (id.equals(playlist.getId())) {
						downloadPlaylistDTO = playlist;
					}
				}
				if(log.isDebugEnabled()){
		            log.debug("the palylist you want to download is:"+downloadPlaylistDTO.getId());
		        }

				// query programlist of playlist
				List<ProgramDTO> programs1 = downloadPlaylistDTO.getProgramList();
				List<ProgramDTO> programs2 = programService.getProgramList();
				List<ProgramDTO> programList = new ArrayList<ProgramDTO>();

				// get the id and name of all the resource
				Map<String,String> resourceMap = new HashMap<String,String>();
				
				// 将带有所有属性的ProgramDTO存到programList中
				for (ProgramDTO program1 : programs1) {
					for (ProgramDTO program2 : programs2) {
						if (program1.getId().equals(program2.getId())) {
							program2.setIndexNum(program1.getIndexNum());
							
							// 获取template的高度，宽度
							List<TemplateDTO> tempTemplateList = templateService.getTemlates();
							for(TemplateDTO template : tempTemplateList){
								if(template.getId().equals(program2.getTemplateId())){
									String size = template.getSize();
									if(log.isDebugEnabled()){
										log.debug("the size of this program for preview is : " + size);
									}
									if("4_3".equals(size)){
										program2.setTemplateWidth("800");
										program2.setTemplateHeight("600");
									}
									if("3_4".equals(size)){
										program2.setTemplateWidth("600");
										program2.setTemplateHeight("800");
									}
									if("5_4".equals(size)){
										program2.setTemplateWidth("800");
										program2.setTemplateHeight("640");
									}
									if("4_5".equals(size)){
										program2.setTemplateWidth("640");
										program2.setTemplateHeight("800");
									}
									if("16_9".equals(size)){
										program2.setTemplateWidth("800");
										program2.setTemplateHeight("450");
									}
									if("9_16".equals(size)){
										program2.setTemplateWidth("450");
										program2.setTemplateHeight("800");
									}
								}
							}
							
							programList.add(program2);
							
							// 获取节目的资源name
							for(OnebyoneDTO onebyone : program2.getOnebyoneList()){
								resourceMap.put(onebyone.getResource(), onebyone.getResourceName());
							}
						}
					}
				}

				downloadPlaylistDTO.setProgramList(programList);

				// 创建目录
				File dirFileFolder = new File(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER);
				File zipFileFolder = new File(FileOperationTool.DEFAULT_ZIP_PATH);
				if(!dirFileFolder.exists()){
					if(!dirFileFolder.mkdir()){
					    if(log.isErrorEnabled()){
					        log.error(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER+"目录创建失败");
					    }
					}
				}
				if(!zipFileFolder.exists()){
					if(!zipFileFolder.mkdir()){
					    if(log.isErrorEnabled()){
                            log.error(FileOperationTool.DEFAULT_ZIP_PATH+"目录创建失败");
                        }
					}
				}
				
				// 创建文件
				String xmlFilePath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER+id+"_"+System.currentTimeMillis()+".xml";
				String zipFilePath = FileOperationTool.DEFAULT_ZIP_PATH+id+"_"+System.currentTimeMillis()+".rar";
				
				// 下载显示的文件名
				fileName = id+"_"+System.currentTimeMillis()+".rar";
				if(log.isDebugEnabled()){
					log.debug("the file name you want to download is :" + fileName);
				}
				File xmlfile = new File(xmlFilePath);
				if(xmlfile.createNewFile()){
					// 创建palylist xml 文件
					XmlOperationTool.newPlaylistXML(downloadPlaylistDTO,xmlFilePath);
					
					// 创建用来打包的文件
					List<File> resourceList = new ArrayList<File>();
					resourceList.add(xmlfile);
					for(Map.Entry<String, String> entry : resourceMap.entrySet()){
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						if(key.contains("video")){
							File resourceFile = new File(FileOperationTool.DEFAULT_VIDEO_DES_PATH+value);
							resourceList.add(resourceFile);
						}
						if(key.contains("audio")){
							File resourceFile = new File(FileOperationTool.DEFAULT_AUDIO_DES_PATH+value);
							resourceList.add(resourceFile);
						}
						if(key.contains("image")){
							File resourceFile = new File(FileOperationTool.DEFAULT_IMG_DES_PATH+value);
							resourceList.add(resourceFile);
						}
						if(key.contains("text")){
							File resourceFile = new File(FileOperationTool.DEFAULT_TEXT_DES_PATH+value);
							resourceList.add(resourceFile);
						}
					}
					
					path = zipFilePath;
					
					// 生成zip文件
					FileOutputStream fous = new FileOutputStream(new File(zipFilePath));  
					ZipOutputStream zipOut = new ZipOutputStream(fous);
					zipOut.setEncoding("gbk");
					FileOperationTool.zipFile(resourceList, zipOut);
					
					zipOut.close();
				}else{
				    if(log.isErrorEnabled()){
                        log.error(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER+id+".xml文件创建失败");
                    }
				}
			}
		} catch (FileNotFoundException e) {
		    if(log.isErrorEnabled()){
		    	log.error("download the playlist failed !!!");
                log.error(e.getMessage());
            }
		    return ERROR;
		} catch (JDOMException e) {
		    if(log.isErrorEnabled()){
		    	log.error("download the playlist failed !!!");
		    	log.error(e.getMessage());
            }
		    return ERROR;
		} catch (IOException e) {
		    if(log.isErrorEnabled()){
		    	log.error("download the playlist failed !!!");
		    	log.error(e.getMessage());
            }
		    return ERROR;
		}
		return SUCCESS;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
