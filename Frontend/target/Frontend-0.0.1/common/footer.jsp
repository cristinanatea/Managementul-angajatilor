
		</div>
	</div>

	<!-- Footer -->
	<footer class="container-fluid bg-4 text-center"></footer>

<script>
function tableSorter(inputField, inputTable, numFields) {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById(inputField);
  filter = input.value.toUpperCase();
  table = document.getElementById(inputTable);
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
	  for (j = 0; j<numFields; j++) {
		td = tr[i].getElementsByTagName("td")[j];
		if (td) {
		  txtValue = td.textContent || td.innerText;
		  if (txtValue.toUpperCase().indexOf(filter) > -1) {
		    tr[i].style.display = "";
		    break;
		  } else {
		    tr[i].style.display = "none";
		  }
		}
	  }
  }
}
</script>