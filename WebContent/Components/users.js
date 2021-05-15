$(document).ready(function()
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 
$.ajax( 
{ 
		url : "UsersAPI", 
		type : type, 
		data : $("#formUser").serialize(), 
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onUserSaveComplete(response.responseText, status); 
		} 
	}); 
});

function onUserSaveComplete(response, status)
{ 
if (status == "success") 
 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully saved."); 
		 $("#alertSuccess").show();
		 
		 $("#divUsersGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
	 } 
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
 }

	$("#hidUserIDSave").val(""); 
	$("#formUser")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
 $("#hidUserIDSave").val($(this).closest("tr").find('#hidUserIDUpdate').val()); 
 $("#userName").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#email").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#password").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#address").val($(this).closest("tr").find('td:eq(3)').text()); 
}); 

//REMOVE=======================================================

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		 url : "UsersAPI", 
		 type : "DELETE", 
		 data : "userID=" + $(this).data("userid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
			 onProductDeleteComplete(response.responseText, status); 
		 } 
	}); 
});

function onProductDeleteComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
		 $("#alertSuccess").text("Successfully deleted."); 
		 $("#alertSuccess").show(); 
		 
		 $("#divUsersGrid").html(resultSet.data); 
		} else if (resultSet.status.trim() == "error") 
		{ 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		} 
		
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
 } 

}

// CLIENT-MODEL================================================================
function validateItemForm() 
{ 
// CODE
if ($("#userName").val().trim() == "") 
 { 
 return "Insert User Name.."; 
 } 
// NAME
if ($("#email").val().trim() == "") 
 { 
 return "Insert Email."; 
 }

//PRICE-------------------------------
if ($("#password").val().trim() == "") 
 { 
 return "Insert Password"; 
 } 
//// is numerical value
//var tmpPrice = $("#productPrice").val().trim(); 
//if (!$.isNumeric(tmpPrice)) 
// { 
// return "Insert a numerical value for Product Price."; 
// } 
//// convert to decimal price
// $("#productPrice").val(parseFloat(tmpPrice).toFixed(2)); 
// DESCRIPTION------------------------
if ($("#address").val().trim() == "") 
 { 
 return "Insert Product Address."; 
 } 
return true; 
}