$(document).ready(function(){
	console.log('start');
	//$('#fileUploadform').submit(function(event){
	//$('#uploadBtn').on('click',function(event){
		var r = new Resumable({
			target:'login',
            chunkSize:2*1024*1024,
            simultaneousUploads:4,
            //testChunks: true,
            throttleProgressCallbacks:1,
            method: "octet"
		});
		r.assignBrowse(document.getElementById('uploadBtn2'));
		if(r.support){
			r.on('fileAdded', function(file){
				console.log(r.files[0].size);
				r.upload();
			});
			r.on('complete', function(){
				$('#successMsg').show().fadeOut(1500);
			});
		}
	//	event.preventDefault();
	//});
});