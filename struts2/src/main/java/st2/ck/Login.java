package st2.ck;

import com.opensymphony.xwork2.ActionSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import st2.ck.util.HttpUtils;
import st2.ck.util.ResumableInfo;
import st2.ck.util.ResumableInfoStorage;
 
@SuppressWarnings("serial")
public class Login extends ActionSupport implements ServletRequestAware {
//public class Login extends ActionSupport {
     
    public String execute() throws Exception {
        //if("admin".equals(getName()) && "admin".equals(getPwd())) {
    	String method = request.getMethod();
    	System.out.println("method: "+ method );
    	
    	int ChunkNumber = Integer.parseInt(getResumableChunkNumber());
    	ResumableInfo info = getResumableInfo();
    	
    	if (method.equals("GET")) {
    		if(info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(ChunkNumber))) {
    			return "SUCCESS";
    		} else {
    			return "Upload_Not_Finished";
    		}
    	}
    	
    	//Map testMap = request.getParameterMap();
    	//for (Object key : testMap.keySet()) {
    	//	System.out.println(key);
    	//}
        	
        	if (!info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(ChunkNumber))) {
	        	RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");
	        	raf.seek((ChunkNumber - 1) * (long)info.resumableChunkSize);
	        	
	        	System.out.println("Log> Upload "+info.resumableFilename+", Chunk.. "+getResumableChunkNumber());
	        	
	        	InputStream is = request.getInputStream();
	        	//Save to file
		        long readed = 0;
		        long content_length = request.getContentLength();
		        System.out.println("ContentsLength> "+request.getInputStream());
		        System.out.println("ContentLength> "+content_length);
		        byte[] bytes = new byte[1024 * 100];
		        while(readed < content_length) {
		            int r = is.read(bytes);
		            if (r < 0)  {
		                break;
		            }
		            System.out.println("write> "+bytes.toString() + " reads/"+readed);
		            raf.write(bytes, 0, r);
		            readed += r;
		        }
		        raf.close();
		        is.close();
        	}
        	
        	info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(ChunkNumber));
        	if (info.checkIfUploadFinished()) { //Check if all chunks uploaded, and change filename
        		ResumableInfoStorage.getInstance().remove(info);
        		System.out.println("Finished");
            } else {
            	System.out.println("Not yet .."+ChunkNumber);
            }
        	
/*        	if (vfile != null){
        		System.out.println("Process.. "+vfile.getPath());
        		String inputFullPath = vfile.getPath();
        		
        		FileInputStream fileInputStream = new FileInputStream(inputFullPath);
        		BufferedReader reader = new BufferedReader(new InputStreamReader( fileInputStream ));
        		String line;
        		while((line = reader.readLine()) != null){
        			System.out.println(" > "+line);
        		}
        		reader.close();
        		fileInputStream.close();
        	}*/
        	return "SUCCESS";
    }
     
    //Java Bean to hold the form parameters
    private HttpServletRequest request;
    
    private String name;
    private String pwd;
    /*private File vfile;
	private String vfilename;
	private String vcontentType;*/
	private String resumableChunkSize;
	private String resumableTotalSize;
	private String resumableIdentifier;
	private String resumableFilename;
	private String resumableRelativePath;
	private String resumableChunkNumber;
	
	public static final String UPLOAD_DIR = "/home/s4553711/tmp/struts2";

    public void setServletRequest(HttpServletRequest httpServletRequest) {
    	this.request = httpServletRequest;
    }
	
	// ResumableUpload related method
	private ResumableInfo getResumableInfo() {
        String base_dir = UPLOAD_DIR;

        int resumableChunkSize          = HttpUtils.toInt(getResumableChunkSize(), -1);
        long resumableTotalSize         = HttpUtils.toLong(getResumableTotalSize(), -1);
        String resumableIdentifier      = getResumableIdentifier();
        String resumableFilename        = getResumableFilename();
        String resumableRelativePath    = getResumableRelativePath();
        //Here we add a ".temp" to every upload file to indicate NON-FINISHED
        String resumableFilePath        = new File(base_dir, resumableFilename).getAbsolutePath() + ".temp";

        ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

        ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize,
                resumableIdentifier, resumableFilename, resumableRelativePath, resumableFilePath);
        
        if (!info.vaild())         {
            storage.remove(info);
            System.out.println("Invalid request params.");
        }
        return info;
    }
	
	// Parameters for basic file upload
    /*public void setVcfile(File uploadedFile) {
    	System.out.println("get1> "+uploadedFile.toString());
    	this.vfile = uploadedFile;
    }
    public File getVcfile() {
    	return vfile;
    }
	public String getVcfileFileName() {
		return vfilename;
	}
 
	public void setVcfileFileName(String fileUploadFileName) {
		this.vfilename = fileUploadFileName;
	}    

	public String getVcfileContentType() {
		return vcontentType;
	}
 
	public void setVcfileContentType(String fileUploadContentType) {
		this.vcontentType = fileUploadContentType;
	}*/
	
	// Default
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    // Parameters for resumable upload
    public void setResumableChunkSize(String size) {
    	this.resumableChunkSize = size;
    }
    public String getResumableChunkSize() {
    	return this.resumableChunkSize;
    }
    public void setResumableTotalSize(String size) {
    	this.resumableTotalSize = size;
    }
    public String getResumableTotalSize() {
    	return this.resumableTotalSize;
    }
    public void setResumableIdentifier(String identifier) {
    	this.resumableIdentifier = identifier;
    }
    public String getResumableIdentifier() {
    	return this.resumableIdentifier;
    }
    public void setResumableFilename(String resumFile) {
    	this.resumableFilename = resumFile;
    }
    public String getResumableFilename() {
    	return this.resumableFilename;
    }
    public void setResumableRelativePath(String relativePath) {
    	this.resumableRelativePath = relativePath;
    }
    public String getResumableRelativePath() {
    	return this.resumableRelativePath;
    }
    public void setResumableChunkNumber(String chunk) {
    	this.resumableChunkNumber = chunk;
    }
    public String getResumableChunkNumber() {
    	return this.resumableChunkNumber;
    } 
}