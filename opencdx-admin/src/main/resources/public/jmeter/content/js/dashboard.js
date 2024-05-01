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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9898785425101214, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Logistics /  List Countries"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Get User"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / GetOrganizationDetailsById"], "isController": false}, {"data": [1.0, 500, 1500, "Media / GetMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Current User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Update Country"], "isController": false}, {"data": [1.0, 500, 1500, "Media"], "isController": true}, {"data": [1.0, 500, 1500, "Connected Test /  Get Country"], "isController": false}, {"data": [1.0, 500, 1500, "/iam/user/verify [GET]"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Manufacturers"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics - Devices Vendor Manufacturers TestCases"], "isController": true}, {"data": [1.0, 500, 1500, "IAM / GetWorkspaceDetailsById"], "isController": false}, {"data": [0.5, 500, 1500, "IAM"], "isController": true}, {"data": [1.0, 500, 1500, "Logistics /  Update TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Signup"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Media / CreateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Users"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Add Country"], "isController": false}, {"data": [1.0, 500, 1500, "Properties Details"], "isController": false}, {"data": [1.0, 500, 1500, "/media/download"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Device"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Create User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Update User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Vendors"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Change Password"], "isController": false}, {"data": [1.0, 500, 1500, "Thread Setup"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Devices"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Organization"], "isController": false}, {"data": [1.0, 500, 1500, "AuditService / Event"], "isController": false}, {"data": [1.0, 500, 1500, "Audit"], "isController": true}, {"data": [1.0, 500, 1500, "/media/upload"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Login"], "isController": false}, {"data": [1.0, 500, 1500, "Media / ListMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Testcaes"], "isController": false}, {"data": [1.0, 500, 1500, "Media / UpdateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Workspace"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 454, 0, 0.0, 37.17180616740087, 0, 401, 22.0, 42.0, 141.25, 372.89999999999975, 7.382233857461097, 27.95356365042521, 1.9684803055740743], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Logistics /  List Countries", 10, 0, 0.0, 24.099999999999998, 18, 39, 19.0, 38.9, 39.0, 39.0, 6.39386189258312, 28.990918718030688, 0.0], "isController": false}, {"data": ["IAM / Update Workspace", 20, 0, 0.0, 23.700000000000003, 20, 38, 22.5, 28.700000000000006, 37.55, 38.0, 4.468275245755138, 104.24521056747096, 0.0], "isController": false}, {"data": ["IAM / Get User", 10, 0, 0.0, 24.2, 22, 30, 23.0, 29.8, 30.0, 30.0, 7.423904974016332, 3.2051839736451373, 0.0], "isController": false}, {"data": ["IAM / Update User", 10, 0, 0.0, 25.5, 22, 32, 24.0, 32.0, 32.0, 32.0, 7.256894049346879, 3.1330789640783747, 0.0], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 0, 0.0, 19.400000000000002, 17, 27, 18.0, 26.6, 27.0, 27.0, 6.176652254478073, 3.148644996911674, 0.0], "isController": false}, {"data": ["IAM / Update Organization", 10, 0, 0.0, 23.0, 20, 30, 22.0, 29.6, 30.0, 30.0, 5.973715651135007, 6.014551597968937, 0.0], "isController": false}, {"data": ["Logistics /  Update Device", 10, 0, 0.0, 24.5, 21, 30, 24.0, 29.700000000000003, 30.0, 30.0, 6.242197253433208, 5.108360642946317, 0.0], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 0, 0.0, 22.0, 18, 35, 19.0, 34.6, 35.0, 35.0, 6.501950585175552, 7.651221147594278, 0.0], "isController": false}, {"data": ["Media / GetMedia", 10, 0, 0.0, 19.0, 17, 23, 18.5, 22.700000000000003, 23.0, 23.0, 5.871990604815032, 4.197555783910746, 0.0], "isController": false}, {"data": ["Logistics /  Get Device", 10, 0, 0.0, 19.1, 17, 27, 18.5, 26.200000000000003, 27.0, 27.0, 6.142506142506142, 6.02253531941032, 0.0], "isController": false}, {"data": ["IAM / Current User", 10, 0, 0.0, 23.299999999999997, 22, 27, 23.0, 26.8, 27.0, 27.0, 7.598784194528876, 3.2287412139057747, 0.0], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 0, 0.0, 22.200000000000003, 20, 28, 21.0, 27.8, 28.0, 28.0, 6.075334143377886, 4.414109963547995, 0.0], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 0, 0.0, 24.799999999999997, 20, 38, 20.0, 37.5, 38.0, 38.0, 5.861664712778429, 1.2478934642438453, 0.0], "isController": false}, {"data": ["Media", 10, 0, 0.0, 111.5, 94, 143, 104.5, 141.8, 143.0, 143.0, 6.116207951070336, 124.09570909785933, 72.7165758792049], "isController": true}, {"data": ["Connected Test /  Get Country", 10, 0, 0.0, 22.1, 18, 36, 20.0, 35.400000000000006, 36.0, 36.0, 5.85480093676815, 2.2069855093676813, 0.0], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 0, 0.0, 122.0, 101, 158, 115.5, 156.4, 158.0, 158.0, 63.29113924050633, 31.52195411392405, 13.721321202531646], "isController": false}, {"data": ["Logistics /  List Manufacturers", 10, 0, 0.0, 24.1, 19, 34, 20.0, 33.8, 34.0, 34.0, 6.447453255963894, 62.944521679561575, 0.0], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 0, 0.0, 22.700000000000003, 21, 26, 22.5, 25.9, 26.0, 26.0, 6.230529595015576, 4.36258761682243, 0.0], "isController": false}, {"data": ["Logistics - Devices Vendor Manufacturers TestCases", 10, 0, 0.0, 380.70000000000005, 341, 421, 373.5, 420.7, 421.0, 421.0, 4.608294930875576, 222.2332229262673, 0.0], "isController": true}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 0, 0.0, 19.9, 17, 33, 18.0, 31.800000000000004, 33.0, 33.0, 6.172839506172839, 8.355034722222221, 0.0], "isController": false}, {"data": ["IAM", 10, 0, 0.0, 1130.2, 1034, 1232, 1143.5, 1228.7, 1232.0, 1232.0, 3.536067892503536, 352.0721910360679, 0.7666084688826026], "isController": true}, {"data": ["Logistics /  Update TestCase", 10, 0, 0.0, 23.999999999999996, 19, 39, 21.0, 37.900000000000006, 39.0, 39.0, 5.546311702717693, 2.063617928452579, 0.0], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 0, 0.0, 22.2, 19, 32, 21.0, 31.400000000000002, 32.0, 32.0, 5.717552887364208, 2.3339229559748427, 0.0], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 0, 0.0, 25.4, 19, 43, 21.5, 42.5, 43.0, 43.0, 5.963029218843173, 4.338336687537269, 0.0], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 0, 0.0, 22.2, 20, 26, 22.0, 25.700000000000003, 26.0, 26.0, 6.297229219143577, 2.189271095717884, 0.0], "isController": false}, {"data": ["IAM / Signup", 10, 0, 0.0, 341.1000000000001, 229, 401, 354.5, 399.6, 401.0, 401.0, 24.93765586034913, 6.3829683603491265, 0.0], "isController": false}, {"data": ["IAM / List Organization", 10, 0, 0.0, 25.299999999999997, 21, 57, 21.5, 53.60000000000001, 57.0, 57.0, 5.878894767783657, 232.40579989711932, 0.0], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 0, 0.0, 19.9, 17, 32, 18.0, 30.900000000000006, 32.0, 32.0, 7.434944237918216, 6.607225836431227, 0.0], "isController": false}, {"data": ["Media / CreateMedia", 10, 0, 0.0, 25.2, 19, 63, 21.0, 59.100000000000016, 63.0, 63.0, 6.049606775559589, 2.995264292196007, 0.0], "isController": false}, {"data": ["IAM / List Users", 10, 0, 0.0, 39.599999999999994, 37, 44, 39.0, 43.7, 44.0, 44.0, 7.961783439490445, 32.321419685509554, 0.0], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 0, 0.0, 22.6, 21, 25, 23.0, 24.8, 25.0, 25.0, 6.70690811535882, 1.4409372904091213, 0.0], "isController": false}, {"data": ["Properties Details", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 0.0, 0.0], "isController": false}, {"data": ["/media/download", 10, 0, 0.0, 8.2, 6, 14, 7.5, 13.600000000000001, 14.0, 14.0, 5.899705014749262, 65.87619837758112, 2.9504286504424777], "isController": false}, {"data": ["Logistics /  Add Device", 10, 0, 0.0, 24.1, 21, 28, 24.0, 27.8, 28.0, 28.0, 6.506180871828237, 5.324394111906312, 0.0], "isController": false}, {"data": ["Health / Create User Profile", 10, 0, 0.0, 31.1, 26, 49, 29.5, 47.50000000000001, 49.0, 49.0, 6.5274151436031325, 24.834774804177545, 0.0], "isController": false}, {"data": ["Health / Update User Profile", 3, 0, 0.0, 26.666666666666668, 25, 29, 26.0, 29.0, 29.0, 29.0, 4.784688995215311, 0.098123504784689, 0.0], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 0, 0.0, 19.9, 18, 23, 20.0, 22.8, 23.0, 23.0, 5.652911249293386, 53.60880970887507, 0.0], "isController": false}, {"data": ["IAM / Change Password", 10, 0, 0.0, 190.5, 181, 219, 186.0, 217.4, 219.0, 219.0, 6.20347394540943, 2.678277178970223, 0.0], "isController": false}, {"data": ["Thread Setup", 10, 0, 0.0, 0.8999999999999999, 0, 2, 1.0, 1.9000000000000004, 2.0, 2.0, 12.195121951219512, 0.0, 0.0], "isController": false}, {"data": ["Logistics /  List Devices", 10, 0, 0.0, 23.799999999999997, 18, 38, 22.0, 37.2, 38.0, 38.0, 6.345177664974619, 67.78930044416244, 0.0], "isController": false}, {"data": ["IAM / Create Organization", 10, 0, 0.0, 22.900000000000002, 20, 33, 22.0, 32.1, 33.0, 33.0, 6.042296072507553, 6.08360083081571, 0.0], "isController": false}, {"data": ["AuditService / Event", 10, 0, 0.0, 24.8, 20, 32, 22.0, 31.9, 32.0, 32.0, 5.624296962879639, 0.11534202755905512, 0.0], "isController": false}, {"data": ["Audit", 10, 0, 0.0, 24.8, 20, 32, 22.0, 31.9, 32.0, 32.0, 5.624296962879639, 0.11534202755905512, 0.0], "isController": true}, {"data": ["/media/upload", 10, 0, 0.0, 13.799999999999999, 11, 23, 11.5, 22.8, 23.0, 23.0, 6.082725060827251, 3.4369772658150852, 69.27653588807786], "isController": false}, {"data": ["IAM / Login", 10, 0, 0.0, 112.3, 102, 125, 111.5, 124.9, 125.0, 125.0, 7.547169811320755, 2.3223761792452833, 0.0], "isController": false}, {"data": ["Media / ListMedia", 10, 0, 0.0, 21.8, 19, 32, 19.5, 31.700000000000003, 32.0, 32.0, 6.480881399870382, 43.35355233311731, 0.0], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 0, 0.0, 21.200000000000003, 17, 37, 18.0, 36.7, 37.0, 37.0, 5.640157924421883, 3.216652566271856, 0.0], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 0, 0.0, 21.9, 18, 38, 19.0, 37.2, 38.0, 38.0, 5.649717514124294, 33.291401836158194, 0.0], "isController": false}, {"data": ["Media / UpdateMedia", 10, 0, 0.0, 23.5, 19, 35, 22.0, 34.2, 35.0, 35.0, 5.743825387708214, 3.7862130241240664, 0.0], "isController": false}, {"data": ["IAM / Create Workspace", 10, 0, 0.0, 21.7, 21, 24, 21.0, 23.9, 24.0, 24.0, 6.622516556291391, 7.838369205298013, 0.0], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 454, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
