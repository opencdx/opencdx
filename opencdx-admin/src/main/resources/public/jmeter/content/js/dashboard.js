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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9715909090909091, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Logistics /  List Countries"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Update"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Get User"], "isController": false}, {"data": [1.0, 500, 1500, "Height / List"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Device"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Create"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / GetOrganizationDetailsById"], "isController": false}, {"data": [1.0, 500, 1500, "Media / GetMedia"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Current User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Height / Update"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/UpdateSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / List"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Update Country"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Update"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / Get"], "isController": false}, {"data": [0.95, 500, 1500, "Media"], "isController": true}, {"data": [1.0, 500, 1500, "Connected Test /  Get Country"], "isController": false}, {"data": [0.5, 500, 1500, "/iam/user/verify [GET]"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Manufacturers"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Vendor"], "isController": false}, {"data": [0.95, 500, 1500, "Logistics - Devices Vendor Manufacturers TestCases"], "isController": true}, {"data": [1.0, 500, 1500, "IAM / GetWorkspaceDetailsById"], "isController": false}, {"data": [0.0, 500, 1500, "IAM"], "isController": true}, {"data": [1.0, 500, 1500, "CommunicationService/UpdateEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Get User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / List"], "isController": false}, {"data": [0.95, 500, 1500, "Health Profile"], "isController": true}, {"data": [1.0, 500, 1500, "BPM / Create"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/listEmailTemplates"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetSMSTemplate (Cache)"], "isController": false}, {"data": [0.5, 500, 1500, "IAM / Signup"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Height / Get"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Media / CreateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Users"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Add Country"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / Update"], "isController": false}, {"data": [1.0, 500, 1500, "Properties Details"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Get"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Create"], "isController": false}, {"data": [0.95, 500, 1500, "Health- Vitals"], "isController": true}, {"data": [1.0, 500, 1500, "CommunicationService/GetEmailTemplate (Cache)"], "isController": false}, {"data": [1.0, 500, 1500, "/media/download"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Device"], "isController": false}, {"data": [0.95, 500, 1500, "Health / Create User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Update User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Vendors"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Change Password"], "isController": false}, {"data": [1.0, 500, 1500, "Thread Setup"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Devices"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateNotificationEvent"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Organization"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/listSMSTemplates"], "isController": false}, {"data": [1.0, 500, 1500, "AuditService / Event"], "isController": false}, {"data": [1.0, 500, 1500, "Audit"], "isController": true}, {"data": [1.0, 500, 1500, "Height / Create"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Get"], "isController": false}, {"data": [1.0, 500, 1500, "/media/upload"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Login"], "isController": false}, {"data": [1.0, 500, 1500, "Media / ListMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Testcaes"], "isController": false}, {"data": [1.0, 500, 1500, "Media / UpdateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / List"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 732, 0, 0.0, 65.73497267759564, 0, 897, 27.0, 145.80000000000018, 280.05000000000007, 841.3499999999998, 12.029976334472783, 16.73470676932685, 1.9887221539738364], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Logistics /  List Countries", 10, 0, 0.0, 23.9, 21, 37, 22.0, 35.900000000000006, 37.0, 37.0, 13.10615989515072, 59.42568397771953, 0.0], "isController": false}, {"data": ["IAM / Update Workspace", 20, 0, 0.0, 30.699999999999992, 22, 60, 28.0, 44.500000000000014, 59.249999999999986, 60.0, 8.176614881439084, 59.5759019828291, 0.0], "isController": false}, {"data": ["Weight / Update", 10, 0, 0.0, 30.599999999999998, 23, 48, 27.0, 47.5, 48.0, 48.0, 11.904761904761903, 5.3827194940476195, 0.0], "isController": false}, {"data": ["IAM / Get User", 10, 0, 0.0, 36.6, 32, 49, 35.0, 48.300000000000004, 49.0, 49.0, 9.652509652509652, 4.167357927123552, 0.0], "isController": false}, {"data": ["Height / List", 10, 0, 0.0, 30.5, 23, 40, 29.0, 39.8, 40.0, 40.0, 12.87001287001287, 7.8300957207207205, 0.0], "isController": false}, {"data": ["IAM / Update User", 10, 0, 0.0, 78.89999999999999, 34, 171, 57.0, 169.70000000000002, 171.0, 171.0, 8.936550491510276, 3.8582509495084896, 0.0], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 0, 0.0, 22.200000000000003, 20, 29, 21.5, 28.5, 29.0, 29.0, 13.03780964797914, 6.6462271838331155, 0.0], "isController": false}, {"data": ["IAM / Update Organization", 10, 0, 0.0, 32.2, 27, 41, 31.5, 40.6, 41.0, 41.0, 12.853470437017995, 12.941335957583547, 0.0], "isController": false}, {"data": ["Logistics /  Update Device", 10, 0, 0.0, 36.3, 28, 65, 31.5, 63.900000000000006, 65.0, 65.0, 12.738853503184714, 10.424960191082802, 0.0], "isController": false}, {"data": ["Heart / Create", 10, 0, 0.0, 29.299999999999997, 22, 64, 25.5, 60.90000000000001, 64.0, 64.0, 12.987012987012989, 4.071124188311688, 0.0], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 0, 0.0, 24.9, 22, 39, 23.5, 37.60000000000001, 39.0, 39.0, 12.970168612191959, 15.26274724383917, 0.0], "isController": false}, {"data": ["Media / GetMedia", 10, 0, 0.0, 24.3, 21, 34, 22.5, 33.8, 34.0, 34.0, 13.123359580052494, 9.376025262467191, 0.0], "isController": false}, {"data": ["CommunicationService/CreateSMSTemplate", 10, 0, 0.0, 31.400000000000002, 20, 95, 23.0, 88.80000000000003, 95.0, 95.0, 12.836970474967908, 1.567012997432606, 0.0], "isController": false}, {"data": ["Logistics /  Get Device", 10, 0, 0.0, 21.8, 19, 29, 21.0, 28.400000000000002, 29.0, 29.0, 13.140604467805518, 12.883952036793692, 0.0], "isController": false}, {"data": ["IAM / Current User", 10, 0, 0.0, 75.0, 47, 150, 66.0, 143.3, 150.0, 150.0, 11.428571428571429, 4.856026785714286, 0.0], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 0, 0.0, 31.799999999999997, 26, 59, 29.0, 56.20000000000001, 59.0, 59.0, 13.054830287206265, 9.485150130548302, 0.0], "isController": false}, {"data": ["Height / Update", 10, 0, 0.0, 27.0, 24, 37, 26.0, 36.2, 37.0, 37.0, 13.03780964797914, 3.870599739243807, 0.0], "isController": false}, {"data": ["CommunicationService/UpdateSMSTemplate", 10, 0, 0.0, 21.4, 21, 22, 21.0, 22.0, 22.0, 22.0, 13.280212483399735, 1.698933432934927, 0.0], "isController": false}, {"data": ["Heart / List", 10, 0, 0.0, 25.700000000000003, 23, 39, 24.0, 37.800000000000004, 39.0, 39.0, 13.140604467805518, 8.264208278580815, 0.0], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 0, 0.0, 31.7, 26, 39, 31.0, 39.0, 39.0, 39.0, 13.333333333333334, 2.8385416666666665, 0.0], "isController": false}, {"data": ["Heart / Update", 10, 0, 0.0, 35.99999999999999, 27, 55, 31.0, 54.6, 55.0, 55.0, 12.610340479192939, 6.145078026481714, 0.0], "isController": false}, {"data": ["BPM / Get", 10, 0, 0.0, 26.2, 22, 36, 23.5, 35.7, 36.0, 36.0, 12.78772378516624, 7.492806905370844, 0.0], "isController": false}, {"data": ["Media", 10, 0, 0.0, 274.4, 119, 561, 220.5, 552.9000000000001, 561.0, 561.0, 9.182736455463727, 126.56034779614325, 109.12929005968779], "isController": true}, {"data": ["Connected Test /  Get Country", 10, 0, 0.0, 28.9, 23, 51, 25.0, 49.60000000000001, 51.0, 51.0, 12.004801920768308, 4.525247599039616, 0.0], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 0, 0.0, 688.6, 661, 729, 687.0, 726.5, 729.0, 729.0, 13.717421124828531, 6.831918724279835, 2.9738940329218106], "isController": false}, {"data": ["Logistics /  List Manufacturers", 10, 0, 0.0, 22.7, 19, 33, 21.5, 32.2, 33.0, 33.0, 13.245033112582782, 2.4446399006622515, 0.0], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 0, 0.0, 28.1, 24, 37, 27.0, 36.400000000000006, 37.0, 37.0, 13.003901170351105, 9.105270643693109, 0.0], "isController": false}, {"data": ["Logistics - Devices Vendor Manufacturers TestCases", 10, 0, 0.0, 450.6, 403, 623, 433.0, 607.0, 623.0, 623.0, 7.347538574577516, 173.62865080822925, 0.0], "isController": true}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 0, 0.0, 29.9, 21, 54, 26.0, 52.50000000000001, 54.0, 54.0, 13.003901170351105, 17.600983420026008, 0.0], "isController": false}, {"data": ["IAM", 10, 0, 0.0, 2969.3, 2633, 3391, 2907.5, 3389.7, 3391.0, 3391.0, 2.46669955599408, 97.5011177232363, 0.534772755303404], "isController": true}, {"data": ["CommunicationService/UpdateEmailTemplate", 10, 0, 0.0, 24.099999999999998, 21, 31, 23.5, 30.700000000000003, 31.0, 31.0, 12.836970474967908, 1.8553433889602053, 0.0], "isController": false}, {"data": ["Logistics /  Update TestCase", 10, 0, 0.0, 25.299999999999997, 23, 30, 24.5, 29.700000000000003, 30.0, 30.0, 13.123359580052494, 4.8828125, 0.0], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 0, 0.0, 28.3, 24, 37, 27.0, 36.6, 37.0, 37.0, 13.071895424836601, 5.3359885620915035, 0.0], "isController": false}, {"data": ["Health / Get User Profile", 10, 0, 0.0, 31.900000000000002, 27, 51, 28.5, 49.50000000000001, 51.0, 51.0, 12.987012987012989, 53.837763798701296, 0.0], "isController": false}, {"data": ["CommunicationService/CreateEmailTemplate", 10, 0, 0.0, 87.1, 25, 295, 32.0, 287.3, 295.0, 295.0, 12.004801920768308, 1.6881752701080432, 0.0], "isController": false}, {"data": ["Weight / List", 10, 0, 0.0, 25.6, 22, 32, 24.5, 31.700000000000003, 32.0, 32.0, 13.071895424836601, 7.761437908496732, 0.0], "isController": false}, {"data": ["Health Profile", 10, 0, 0.0, 288.1, 110, 600, 250.5, 589.2, 600.0, 600.0, 9.140767824497258, 74.79540390767824, 0.0], "isController": true}, {"data": ["BPM / Create", 10, 0, 0.0, 27.0, 23, 48, 24.5, 45.900000000000006, 48.0, 48.0, 12.903225806451612, 5.367943548387097, 0.0], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 0, 0.0, 27.5, 25, 30, 27.0, 29.9, 30.0, 30.0, 12.642225031605562, 9.197712547408344, 0.0], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 0, 0.0, 28.700000000000003, 25, 40, 27.0, 39.1, 40.0, 40.0, 13.123359580052494, 4.562417979002625, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate", 10, 0, 0.0, 20.5, 18, 32, 19.0, 30.900000000000006, 32.0, 32.0, 13.192612137203167, 3.7490723944591027, 0.0], "isController": false}, {"data": ["CommunicationService/listEmailTemplates", 10, 0, 0.0, 28.2, 20, 69, 23.5, 65.10000000000002, 69.0, 69.0, 13.089005235602095, 60.255440117801044, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate (Cache)", 10, 0, 0.0, 17.799999999999997, 16, 22, 18.0, 21.6, 22.0, 22.0, 13.123359580052494, 3.7293922244094486, 0.0], "isController": false}, {"data": ["IAM / Signup", 10, 0, 0.0, 838.8000000000001, 658, 897, 843.0, 897.0, 897.0, 897.0, 10.729613733905579, 2.7463200777896994, 0.0], "isController": false}, {"data": ["IAM / List Organization", 10, 0, 0.0, 27.0, 24, 34, 26.0, 33.5, 34.0, 34.0, 13.054830287206265, 151.3671875, 0.0], "isController": false}, {"data": ["Height / Get", 10, 0, 0.0, 28.3, 22, 37, 25.0, 36.9, 37.0, 37.0, 13.66120218579235, 6.3770064890710385, 0.0], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 0, 0.0, 25.8, 22, 36, 23.0, 35.7, 36.0, 36.0, 13.550135501355014, 12.041624322493226, 0.0], "isController": false}, {"data": ["Media / CreateMedia", 10, 0, 0.0, 95.0, 24, 306, 42.5, 298.5, 306.0, 306.0, 13.10615989515072, 6.48908502621232, 0.0], "isController": false}, {"data": ["IAM / List Users", 10, 0, 0.0, 305.6, 206, 437, 313.0, 427.5, 437.0, 437.0, 5.952380952380952, 24.082728794642858, 0.0], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 0, 0.0, 151.3, 31, 433, 79.5, 423.80000000000007, 433.0, 433.0, 12.738853503184714, 2.7368630573248405, 0.0], "isController": false}, {"data": ["BPM / Update", 10, 0, 0.0, 32.5, 23, 49, 27.0, 49.0, 49.0, 49.0, 12.903225806451612, 7.610887096774193, 0.0], "isController": false}, {"data": ["Properties Details", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 0.0, 0.0], "isController": false}, {"data": ["Heart / Get", 10, 0, 0.0, 29.8, 23, 51, 27.5, 49.400000000000006, 51.0, 51.0, 12.953367875647668, 6.261637791450777, 0.0], "isController": false}, {"data": ["Weight / Create", 10, 0, 0.0, 30.099999999999998, 24, 40, 29.0, 39.300000000000004, 40.0, 40.0, 12.936610608020699, 3.6005215071151357, 0.0], "isController": false}, {"data": ["Health- Vitals", 10, 0, 0.0, 464.0, 404, 642, 445.5, 626.9000000000001, 642.0, 642.0, 6.891798759476224, 52.913400241212955, 0.0], "isController": true}, {"data": ["CommunicationService/GetEmailTemplate (Cache)", 10, 0, 0.0, 19.1, 17, 22, 19.0, 21.8, 22.0, 22.0, 12.970168612191959, 3.92651588845655, 0.0], "isController": false}, {"data": ["/media/download", 10, 0, 0.0, 12.5, 8, 20, 11.0, 19.8, 20.0, 20.0, 13.550135501355014, 151.28779217479675, 6.776391006097561], "isController": false}, {"data": ["CommunicationService/GetEmailTemplate", 10, 0, 0.0, 23.1, 21, 27, 23.0, 26.700000000000003, 27.0, 27.0, 12.936610608020699, 3.916356727037516, 0.0], "isController": false}, {"data": ["Logistics /  Add Device", 10, 0, 0.0, 30.8, 26, 57, 28.0, 54.400000000000006, 57.0, 57.0, 13.10615989515072, 10.72554882044561, 0.0], "isController": false}, {"data": ["Health / Create User Profile", 10, 0, 0.0, 215.10000000000002, 42, 516, 167.5, 506.80000000000007, 516.0, 516.0, 12.594458438287154, 50.58692144206549, 0.0], "isController": false}, {"data": ["Health / Update User Profile", 10, 0, 0.0, 41.1, 36, 56, 38.0, 55.400000000000006, 56.0, 56.0, 12.626262626262626, 0.2589370265151515, 0.0], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 0, 0.0, 23.200000000000003, 19, 34, 20.5, 33.6, 34.0, 34.0, 13.03780964797914, 2.329999185136897, 0.0], "isController": false}, {"data": ["IAM / Change Password", 10, 0, 0.0, 237.1, 204, 282, 234.5, 278.8, 282.0, 282.0, 9.124087591240874, 3.939217894616788, 0.0], "isController": false}, {"data": ["Thread Setup", 10, 0, 0.0, 1.7, 0, 12, 1.0, 10.900000000000004, 12.0, 12.0, 13.10615989515072, 0.0, 0.0], "isController": false}, {"data": ["Logistics /  List Devices", 10, 0, 0.0, 24.299999999999997, 21, 32, 23.0, 31.9, 32.0, 32.0, 13.21003963011889, 141.13069682959048, 0.0], "isController": false}, {"data": ["CommunicationService/CreateNotificationEvent", 1, 0, 0.0, 35.0, 35, 35, 35.0, 35.0, 35.0, 35.0, 28.57142857142857, 9.877232142857142, 0.0], "isController": false}, {"data": ["IAM / Create Organization", 10, 0, 0.0, 37.4, 27, 71, 33.0, 68.5, 71.0, 71.0, 12.987012987012989, 13.075791396103895, 0.0], "isController": false}, {"data": ["CommunicationService/listSMSTemplates", 10, 0, 0.0, 23.400000000000002, 18, 32, 21.0, 31.8, 32.0, 32.0, 12.936610608020699, 34.40077215394567, 0.0], "isController": false}, {"data": ["AuditService / Event", 10, 0, 0.0, 63.60000000000001, 25, 227, 31.5, 218.60000000000002, 227.0, 227.0, 12.515644555694617, 0.25666849186483104, 0.0], "isController": false}, {"data": ["Audit", 10, 0, 0.0, 63.60000000000001, 25, 227, 31.5, 218.60000000000002, 227.0, 227.0, 12.515644555694617, 0.25666849186483104, 0.0], "isController": true}, {"data": ["Height / Create", 10, 0, 0.0, 30.0, 26, 44, 29.0, 42.7, 44.0, 44.0, 12.953367875647668, 3.8455310880829017, 0.0], "isController": false}, {"data": ["Weight / Get", 10, 0, 0.0, 27.000000000000004, 23, 39, 25.5, 38.1, 39.0, 39.0, 12.755102040816327, 5.717374840561225, 0.0], "isController": false}, {"data": ["/media/upload", 10, 0, 0.0, 93.39999999999999, 16, 181, 78.0, 180.4, 181.0, 181.0, 21.1864406779661, 11.958752648305085, 241.18817862817798], "isController": false}, {"data": ["IAM / Login", 10, 0, 0.0, 244.9, 168, 412, 222.0, 405.3, 412.0, 412.0, 7.82472613458529, 2.4077843798904537, 0.0], "isController": false}, {"data": ["Media / ListMedia", 10, 0, 0.0, 23.0, 20, 43, 20.5, 41.00000000000001, 43.0, 43.0, 12.56281407035176, 2.318722518844221, 0.0], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 0, 0.0, 23.6, 21, 28, 23.0, 28.0, 28.0, 28.0, 13.071895424836601, 7.455065359477124, 0.0], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 0, 0.0, 26.3, 20, 62, 22.5, 58.60000000000001, 62.0, 62.0, 13.175230566534914, 2.393157114624506, 0.0], "isController": false}, {"data": ["Media / UpdateMedia", 10, 0, 0.0, 26.200000000000003, 23, 36, 25.5, 35.1, 36.0, 36.0, 12.804097311139564, 8.435199263764405, 0.0], "isController": false}, {"data": ["IAM / Create Workspace", 10, 0, 0.0, 39.1, 26, 84, 31.5, 80.70000000000002, 84.0, 84.0, 13.03780964797914, 15.43147001303781, 0.0], "isController": false}, {"data": ["BPM / List", 10, 0, 0.0, 28.4, 22, 43, 25.5, 42.400000000000006, 43.0, 43.0, 11.876484560570072, 8.686998960807601, 0.0], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 732, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
