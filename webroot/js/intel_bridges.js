// ---------------------------------------------------------------

function bridgesToDotlan(bridges) {
	cntdotlan = "";
	cntdotlan += bridges['nameA'] + " " + bridges['planetA'] + "-" + bridges['moonA'];
	cntdotlan += " &lt;-&gt; ";
	cntdotlan += bridges['nameB'] + " " + bridges['planetB'] + "-" + bridges['moonB'];
	return cntdotlan;
}

function bridgesToGts(bridges) {
    var elements = [];
    elements.push("-"); // Region (can be blank)
    elements.push(bridges.nameA + " @ " + bridges.planetA + "-" + bridges.moonA); // System A
    elements.push(bridges.nameB + " @ " + bridges.planetB + "-" + bridges.moonB); // System B
    elements.push("Online"); // Status. TODO: Actually retrieve JB status.
    elements.push("-"); // Owner. TODO: Actually retrieve JB owner.
    elements.push("-"); // Password. TODO: Actually retrieve JB password.
    elements.push("0"); // Distance (in lightyears). TODO: Actually calculate distance.
    if(bridges.friendly) {
	elements.push("Green"); // Color. TODO: Color based on owner.
	elements.push("Yes"); // Friendly. Boolean Yes/No on whether or not this JB is friendly.
    } else {
	elements.push("Red"); // Color. TODO: Color based on owner.
	elements.push("No"); // Friendly. Boolean Yes/No on whether or not this JB is friendly.
    }
    return elements.join("\t");
}

function bridgesUpdate() {
    $("#bridges-dotlan-friendly").html("");
    $("#bridges-dotlan-hostile").html("");
    $("#bridges-gts").html("");

    $("#bridges-gts").append("Region\tSystem / POS\tSystem / POS\tStatus\tOwner\tPassword\tDist (ly)\tRoute\tFriendly\n");

    for (i in jbData['bridges']) {

	cnt = bridgesToDotlan(jbData['bridges'][i]) + "\n";
	if (jbData['bridges'][i]['friendly']) {
	    $("#bridges-dotlan-friendly").append(cnt);
	} else {
	    $("#bridges-dotlan-hostile").append(cnt);
	}

	cnt = bridgesToGts(jbData['bridges'][i]) + "\n";
	$("#bridges-gts").append(cnt);
    }
}

// ---------------------------------------------------------------
