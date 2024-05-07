/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9861751152073732, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Logistics /  List Countries"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Signup"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Organization"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Get User"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Media / CreateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / GetOrganizationDetailsById"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Users"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Add Country"], "isController": false}, {"data": [1.0, 500, 1500, "Media / GetMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Properties Details"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Current User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Device"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Vendors"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Change Password"], "isController": false}, {"data": [1.0, 500, 1500, "Thread Setup"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Update Country"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Devices"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Get Country"], "isController": false}, {"data": [1.0, 500, 1500, "AuditService / Event"], "isController": false}, {"data": [1.0, 500, 1500, "/iam/user/verify [GET]"], "isController": false}, {"data": [1.0, 500, 1500, "Audit"], "isController": true}, {"data": [1.0, 500, 1500, "Logistics /  List Manufacturers"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics - Devices Vendor Manufacturers TestCases"], "isController": true}, {"data": [1.0, 500, 1500, "/media/upload"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / GetWorkspaceDetailsById"], "isController": false}, {"data": [0.4, 500, 1500, "IAM"], "isController": true}, {"data": [1.0, 500, 1500, "Logistics /  Update TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Login"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Testcaes"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add TestCase"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 404, 0, 0.0, 50.04207920792081, 0, 475, 25.0, 126.0, 199.75, 432.84999999999997, 6.569960320041631, 18.54668341003708, 1.3306640752049048], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Logistics /  List Countries", 10, 0, 0.0, 25.200000000000003, 20, 41, 22.0, 40.0, 41.0, 41.0, 5.350454788657036, 24.259923421615838, 0.0], "isController": false}, {"data": ["IAM / Signup", 10, 0, 0.0, 408.5, 289, 460, 424.5, 458.8, 460.0, 460.0, 21.691973969631235, 5.552213259219089, 0.0], "isController": false}, {"data": ["IAM / List Organization", 10, 0, 0.0, 25.1, 22, 29, 24.5, 28.9, 29.0, 29.0, 6.031363088057901, 139.72854154101327, 0.0], "isController": false}, {"data": ["IAM / Update Workspace", 20, 0, 0.0, 24.550000000000004, 22, 42, 23.0, 27.800000000000004, 41.29999999999999, 42.0, 3.68052999631947, 51.41959192123666, 0.0], "isController": false}, {"data": ["IAM / Get User", 10, 0, 0.0, 31.299999999999997, 24, 48, 27.5, 47.8, 48.0, 48.0, 6.30119722747322, 2.7204680608065535, 0.0], "isController": false}, {"data": ["IAM / Update User", 10, 0, 0.0, 34.20000000000001, 28, 47, 32.0, 46.9, 47.0, 47.0, 6.854009595613434, 2.9591383224811514, 0.0], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 0, 0.0, 21.1, 19, 24, 21.0, 23.9, 24.0, 24.0, 5.827505827505828, 5.17874053030303, 0.0], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 0, 0.0, 20.2, 18, 23, 19.5, 22.9, 23.0, 23.0, 5.868544600938967, 2.991582306338028, 0.0], "isController": false}, {"data": ["IAM / Update Organization", 10, 0, 0.0, 24.400000000000002, 22, 29, 23.5, 28.700000000000003, 29.0, 29.0, 6.101281269066504, 6.142989246491763, 0.0], "isController": false}, {"data": ["Media / CreateMedia", 10, 0, 0.0, 86.39999999999999, 23, 475, 25.0, 444.0000000000001, 475.0, 475.0, 6.230529595015576, 3.084842289719626, 0.0], "isController": false}, {"data": ["Logistics /  Update Device", 10, 0, 0.0, 30.1, 25, 46, 28.5, 44.60000000000001, 46.0, 46.0, 5.931198102016607, 4.853851571767497, 0.0], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 0, 0.0, 21.0, 20, 22, 21.0, 22.0, 22.0, 22.0, 5.878894767783657, 6.918035346854791, 0.0], "isController": false}, {"data": ["IAM / List Users", 10, 0, 0.0, 143.20000000000002, 88, 200, 151.0, 199.8, 200.0, 200.0, 5.399568034557235, 21.925199109071272, 0.0], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 0, 0.0, 29.2, 25, 50, 26.0, 48.300000000000004, 50.0, 50.0, 6.830601092896175, 1.4675119535519126, 0.0], "isController": false}, {"data": ["Media / GetMedia", 6, 0, 0.0, 23.833333333333336, 19, 33, 21.5, 33.0, 33.0, 33.0, 7.407407407407407, 5.295138888888888, 0.0], "isController": false}, {"data": ["Properties Details", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 0.0, 0.0], "isController": false}, {"data": ["Logistics /  Get Device", 10, 0, 0.0, 21.5, 19, 34, 20.0, 32.800000000000004, 34.0, 34.0, 6.285355122564425, 6.1625942803268385, 0.0], "isController": false}, {"data": ["IAM / Current User", 10, 0, 0.0, 48.7, 27, 76, 43.0, 75.9, 76.0, 76.0, 7.022471910112359, 2.9838647735252812, 0.0], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 0, 0.0, 26.3, 23, 31, 26.5, 30.700000000000003, 31.0, 31.0, 4.899559039686428, 3.559835864772171, 0.0], "isController": false}, {"data": ["Logistics /  Add Device", 10, 0, 0.0, 30.5, 25, 58, 27.0, 55.20000000000001, 58.0, 58.0, 5.966587112171838, 4.8828125, 0.0], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 0, 0.0, 22.8, 20, 29, 22.0, 28.6, 29.0, 29.0, 5.611672278338944, 53.21772411616161, 0.0], "isController": false}, {"data": ["IAM / Change Password", 10, 0, 0.0, 209.39999999999998, 189, 237, 207.0, 236.2, 237.0, 237.0, 6.053268765133172, 2.6134278526029058, 0.0], "isController": false}, {"data": ["Thread Setup", 10, 0, 0.0, 0.9, 0, 3, 1.0, 2.8000000000000007, 3.0, 3.0, 12.40694789081886, 0.0, 0.0], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 0, 0.0, 26.1, 23, 30, 26.0, 29.8, 30.0, 30.0, 5.924170616113743, 1.26120038507109, 0.0], "isController": false}, {"data": ["Logistics /  List Devices", 10, 0, 0.0, 23.199999999999996, 20, 30, 22.5, 29.5, 30.0, 30.0, 6.086427267194157, 65.02491631162508, 0.0], "isController": false}, {"data": ["IAM / Create Organization", 10, 0, 0.0, 29.3, 24, 54, 26.0, 51.70000000000001, 54.0, 54.0, 5.675368898978434, 5.714165366061294, 0.0], "isController": false}, {"data": ["Connected Test /  Get Country", 10, 0, 0.0, 22.0, 21, 26, 22.0, 25.6, 26.0, 26.0, 6.426735218508997, 2.4225779241645244, 0.0], "isController": false}, {"data": ["AuditService / Event", 10, 0, 0.0, 25.6, 22, 31, 25.0, 30.9, 31.0, 31.0, 4.593477262287552, 0.09420217041800642, 0.0], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 0, 0.0, 173.79999999999998, 93, 229, 174.5, 227.70000000000002, 229.0, 229.0, 42.73504273504273, 21.284054487179485, 9.264823717948717], "isController": false}, {"data": ["Audit", 10, 0, 0.0, 25.6, 22, 31, 25.0, 30.9, 31.0, 31.0, 4.593477262287552, 0.09420217041800642, 0.0], "isController": true}, {"data": ["Logistics /  List Manufacturers", 10, 0, 0.0, 24.0, 21, 28, 23.5, 27.8, 28.0, 28.0, 6.321112515802781, 61.711095527812894, 0.0], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 0, 0.0, 24.5, 22, 27, 24.0, 26.9, 27.0, 27.0, 5.50357732526142, 3.8535790451293344, 0.0], "isController": false}, {"data": ["Logistics - Devices Vendor Manufacturers TestCases", 10, 0, 0.0, 414.8999999999999, 382, 450, 417.5, 449.5, 450.0, 450.0, 4.528985507246377, 218.40855695199272, 0.0], "isController": true}, {"data": ["/media/upload", 7, 0, 0.0, 62.0, 13, 153, 15.0, 153.0, 153.0, 153.0, 10.447761194029852, 5.89727145522388, 118.89138292910447], "isController": false}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 0, 0.0, 22.2, 19, 39, 20.0, 37.300000000000004, 39.0, 39.0, 5.688282138794084, 7.699178754266212, 0.0], "isController": false}, {"data": ["IAM", 10, 0, 0.0, 1435.7999999999997, 1323, 1555, 1424.0, 1551.8, 1555.0, 1555.0, 3.1318509238960224, 201.95116759317256, 0.6789754932665205], "isController": true}, {"data": ["Logistics /  Update TestCase", 10, 0, 0.0, 24.5, 21, 31, 24.0, 30.6, 31.0, 31.0, 6.12369871402327, 2.2784464941824862, 0.0], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 0, 0.0, 25.3, 22, 31, 24.0, 30.9, 31.0, 31.0, 5.649717514124294, 2.306232344632768, 0.0], "isController": false}, {"data": ["IAM / Login", 10, 0, 0.0, 114.3, 106, 127, 109.5, 126.8, 127.0, 127.0, 6.920415224913495, 2.129514489619377, 0.0], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 0, 0.0, 20.1, 19, 24, 20.0, 23.700000000000003, 24.0, 24.0, 4.899559039686428, 2.7942797648211664, 0.0], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 0, 0.0, 23.099999999999994, 20, 35, 22.0, 33.900000000000006, 35.0, 35.0, 6.097560975609756, 35.93035442073171, 0.0], "isController": false}, {"data": ["IAM / Create Workspace", 10, 0, 0.0, 24.0, 22, 27, 23.5, 26.9, 27.0, 27.0, 5.55247084952804, 6.5718697945585784, 0.0], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 0, 0.0, 24.6, 23, 30, 23.5, 29.700000000000003, 30.0, 30.0, 6.0938452163315056, 4.433510435709933, 0.0], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 0, 0.0, 27.9, 23, 40, 27.0, 39.2, 40.0, 40.0, 4.7303689687795645, 1.6445423368022707, 0.0], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 404, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
