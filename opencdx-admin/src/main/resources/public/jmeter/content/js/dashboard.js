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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9938042131350682, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Logistics /  List Countries"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Update"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Get User"], "isController": false}, {"data": [1.0, 500, 1500, "Height / List"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Update Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Device"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Create"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / GetOrganizationDetailsById"], "isController": false}, {"data": [1.0, 500, 1500, "Media / GetMedia"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Device"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Current User"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Height / Update"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/UpdateSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / List"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Update Country"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Update"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / Get"], "isController": false}, {"data": [1.0, 500, 1500, "Media"], "isController": true}, {"data": [1.0, 500, 1500, "Connected Test /  Get Country"], "isController": false}, {"data": [1.0, 500, 1500, "/iam/user/verify [GET]"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Manufacturers"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics - Devices Vendor Manufacturers TestCases"], "isController": true}, {"data": [1.0, 500, 1500, "IAM / GetWorkspaceDetailsById"], "isController": false}, {"data": [0.5, 500, 1500, "IAM"], "isController": true}, {"data": [1.0, 500, 1500, "CommunicationService/UpdateEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Get User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / List"], "isController": false}, {"data": [1.0, 500, 1500, "Health Profile"], "isController": true}, {"data": [1.0, 500, 1500, "BPM / Create"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Update Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add TestCase"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetSMSTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/listEmailTemplates"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetSMSTemplate (Cache)"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Signup"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Organization"], "isController": false}, {"data": [1.0, 500, 1500, "Height / Get"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Manufacturer"], "isController": false}, {"data": [1.0, 500, 1500, "Media / CreateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / List Users"], "isController": false}, {"data": [1.0, 500, 1500, "Connected Test /  Add Country"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / Update"], "isController": false}, {"data": [1.0, 500, 1500, "Properties Details"], "isController": false}, {"data": [1.0, 500, 1500, "Heart / Get"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Create"], "isController": false}, {"data": [1.0, 500, 1500, "Health- Vitals"], "isController": true}, {"data": [1.0, 500, 1500, "CommunicationService/GetEmailTemplate (Cache)"], "isController": false}, {"data": [1.0, 500, 1500, "/media/download"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetEmailTemplate"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Add Device"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Create User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Health / Update User Profile"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Vendors"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Change Password"], "isController": false}, {"data": [1.0, 500, 1500, "Thread Setup"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Devices"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/CreateNotificationEvent"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Organization"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/GetNotificationEvent"], "isController": false}, {"data": [1.0, 500, 1500, "CommunicationService/listSMSTemplates"], "isController": false}, {"data": [1.0, 500, 1500, "AuditService / Event"], "isController": false}, {"data": [1.0, 500, 1500, "Audit"], "isController": true}, {"data": [1.0, 500, 1500, "Height / Create"], "isController": false}, {"data": [1.0, 500, 1500, "Weight / Get"], "isController": false}, {"data": [1.0, 500, 1500, "/media/upload"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Login"], "isController": false}, {"data": [1.0, 500, 1500, "Media / ListMedia"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  Get Vendor"], "isController": false}, {"data": [1.0, 500, 1500, "Logistics /  List Testcaes"], "isController": false}, {"data": [1.0, 500, 1500, "Media / UpdateMedia"], "isController": false}, {"data": [1.0, 500, 1500, "IAM / Create Workspace"], "isController": false}, {"data": [1.0, 500, 1500, "BPM / List"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 747, 0, 0.0, 34.51941097724234, 0, 476, 23.0, 46.0, 107.0, 340.71999999999935, 12.293665553050376, 30.67215940416043, 1.4809877361634547], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Logistics /  List Countries", 10, 0, 0.0, 28.3, 20, 62, 22.5, 60.7, 62.0, 62.0, 7.751937984496124, 35.148679748062015, 0.0], "isController": false}, {"data": ["IAM / Update Workspace", 20, 0, 0.0, 24.55, 22, 31, 24.0, 29.0, 30.9, 31.0, 6.870491240123669, 137.31924703710064, 0.0], "isController": false}, {"data": ["Weight / Update", 10, 0, 0.0, 27.5, 22, 45, 24.5, 43.900000000000006, 45.0, 45.0, 7.479431563201197, 3.3818132946896036, 0.0], "isController": false}, {"data": ["IAM / Get User", 10, 0, 0.0, 27.3, 23, 43, 24.0, 42.7, 43.0, 43.0, 12.437810945273633, 5.369879120024875, 0.0], "isController": false}, {"data": ["Height / List", 10, 0, 0.0, 44.900000000000006, 40, 58, 43.5, 57.0, 58.0, 58.0, 7.651109410864575, 35.05762241775057, 0.0], "isController": false}, {"data": ["IAM / Update User", 10, 0, 0.0, 28.0, 23, 49, 25.0, 47.400000000000006, 49.0, 49.0, 12.72264631043257, 5.492853451017812, 0.0], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 0, 0.0, 22.0, 18, 32, 20.0, 31.700000000000003, 32.0, 32.0, 8.340283569641368, 4.251589866555463, 0.0], "isController": false}, {"data": ["IAM / Update Organization", 10, 0, 0.0, 39.7, 28, 59, 35.5, 58.6, 59.0, 59.0, 7.757951900698216, 7.810984775019396, 0.0], "isController": false}, {"data": ["Logistics /  Update Device", 10, 0, 0.0, 25.8, 22, 28, 26.0, 27.9, 28.0, 28.0, 8.547008547008549, 6.994524572649573, 0.0], "isController": false}, {"data": ["Heart / Create", 10, 0, 0.0, 22.3, 20, 27, 21.5, 26.700000000000003, 27.0, 27.0, 7.77000777000777, 2.4357153263403264, 0.0], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 0, 0.0, 28.3, 20, 58, 23.5, 55.50000000000001, 58.0, 58.0, 7.898894154818325, 9.29508540679305, 0.0], "isController": false}, {"data": ["Media / GetMedia", 10, 0, 0.0, 23.200000000000003, 18, 47, 20.5, 44.70000000000001, 47.0, 47.0, 8.333333333333334, 5.948893229166667, 0.0], "isController": false}, {"data": ["CommunicationService/CreateSMSTemplate", 10, 0, 0.0, 21.0, 19, 27, 20.5, 26.5, 27.0, 27.0, 8.183306055646481, 0.9989387274959084, 0.0], "isController": false}, {"data": ["Logistics /  Get Device", 10, 0, 0.0, 20.0, 18, 25, 19.5, 24.6, 25.0, 25.0, 8.361204013377925, 8.19789924749164, 0.0], "isController": false}, {"data": ["IAM / Current User", 10, 0, 0.0, 27.299999999999997, 23, 33, 27.0, 33.0, 33.0, 33.0, 12.987012987012989, 5.5182122564935066, 0.0], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 0, 0.0, 27.5, 22, 41, 25.0, 40.9, 41.0, 41.0, 8.285004142502071, 6.019573322286661, 0.0], "isController": false}, {"data": ["Height / Update", 10, 0, 0.0, 25.799999999999997, 20, 46, 23.5, 44.300000000000004, 46.0, 46.0, 7.818608287724785, 2.3211493354182955, 0.0], "isController": false}, {"data": ["CommunicationService/UpdateSMSTemplate", 10, 0, 0.0, 22.3, 20, 24, 22.5, 24.0, 24.0, 24.0, 7.79423226812159, 0.9971136983632113, 0.0], "isController": false}, {"data": ["Heart / List", 10, 0, 0.0, 25.9, 19, 41, 23.5, 40.5, 41.0, 41.0, 7.776049766718508, 4.890406298600311, 0.0], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 0, 0.0, 28.0, 21, 48, 25.0, 46.900000000000006, 48.0, 48.0, 8.183306055646481, 1.7421491407528642, 0.0], "isController": false}, {"data": ["Heart / Update", 10, 0, 0.0, 22.2, 21, 24, 22.0, 24.0, 24.0, 24.0, 8.136696501220506, 3.965050345809601, 0.0], "isController": false}, {"data": ["BPM / Get", 10, 0, 0.0, 20.6, 19, 27, 20.0, 26.5, 27.0, 27.0, 7.722007722007723, 4.5246138996139, 0.0], "isController": false}, {"data": ["Media", 10, 0, 0.0, 112.7, 98, 155, 103.5, 152.5, 155.0, 155.0, 7.299270072992701, 125.07555885036496, 64.10313070255474], "isController": true}, {"data": ["Connected Test /  Get Country", 10, 0, 0.0, 21.0, 20, 27, 20.0, 26.400000000000002, 27.0, 27.0, 8.347245409015025, 3.146520242070117, 0.0], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 0, 0.0, 126.1, 97, 185, 119.0, 181.5, 185.0, 185.0, 54.054054054054056, 26.9214527027027, 11.71875], "isController": false}, {"data": ["Logistics /  List Manufacturers", 10, 0, 0.0, 19.999999999999996, 19, 21, 20.0, 21.0, 21.0, 21.0, 8.503401360544219, 83.01611660289116, 0.0], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 0, 0.0, 24.9, 21, 42, 22.5, 40.60000000000001, 42.0, 42.0, 7.949125596184419, 5.565940480922099, 0.0], "isController": false}, {"data": ["Logistics - Devices Vendor Manufacturers TestCases", 10, 0, 0.0, 398.2, 371, 424, 397.0, 423.1, 424.0, 424.0, 6.51890482398957, 314.37163868970015, 0.0], "isController": true}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 0, 0.0, 20.1, 18, 22, 20.0, 22.0, 22.0, 22.0, 8.285004142502071, 11.21388256006628, 0.0], "isController": false}, {"data": ["IAM", 10, 0, 0.0, 1285.6, 1136, 1451, 1283.0, 1444.8, 1451.0, 1451.0, 4.033884630899556, 350.60760387252924, 0.8745335820895522], "isController": true}, {"data": ["CommunicationService/UpdateEmailTemplate", 10, 0, 0.0, 21.799999999999997, 20, 23, 21.5, 23.0, 23.0, 23.0, 7.722007722007723, 1.1160714285714286, 0.0], "isController": false}, {"data": ["Logistics /  Update TestCase", 10, 0, 0.0, 25.099999999999998, 20, 54, 21.5, 51.400000000000006, 54.0, 54.0, 8.347245409015025, 3.1057622078464107, 0.0], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 0, 0.0, 24.9, 21, 50, 22.0, 47.400000000000006, 50.0, 50.0, 8.319467554076539, 3.39603265391015, 0.0], "isController": false}, {"data": ["Health / Get User Profile", 10, 0, 0.0, 30.8, 21, 65, 25.0, 64.10000000000001, 65.0, 65.0, 7.745933384972889, 31.021555480247873, 0.0], "isController": false}, {"data": ["CommunicationService/CreateEmailTemplate", 10, 0, 0.0, 22.299999999999997, 20, 25, 22.5, 24.9, 25.0, 25.0, 7.363770250368188, 1.0355301914580264, 0.0], "isController": false}, {"data": ["Weight / List", 10, 0, 0.0, 51.099999999999994, 41, 85, 46.0, 82.4, 85.0, 85.0, 7.547169811320755, 33.475825471698116, 0.0], "isController": false}, {"data": ["Health Profile", 10, 0, 0.0, 89.4, 75, 131, 83.0, 129.10000000000002, 131.0, 131.0, 7.423904974016332, 58.658999164810695, 0.0], "isController": true}, {"data": ["BPM / Create", 10, 0, 0.0, 22.400000000000002, 20, 27, 21.5, 26.9, 27.0, 27.0, 7.782101167315175, 3.2374756809338523, 0.0], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 0, 0.0, 26.2, 22, 31, 25.0, 31.0, 31.0, 31.0, 8.264462809917356, 6.012719524793389, 0.0], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 0, 0.0, 24.599999999999998, 22, 30, 23.5, 29.6, 30.0, 30.0, 7.91765637371338, 2.7526227236737926, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate", 10, 0, 0.0, 19.400000000000002, 17, 21, 19.0, 21.0, 21.0, 21.0, 7.6452599388379205, 2.172627580275229, 0.0], "isController": false}, {"data": ["CommunicationService/listEmailTemplates", 10, 0, 0.0, 20.899999999999995, 19, 23, 20.5, 23.0, 23.0, 23.0, 7.727975270479135, 35.575854907264294, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate (Cache)", 10, 0, 0.0, 17.6, 15, 20, 17.0, 20.0, 20.0, 20.0, 7.757951900698216, 2.20465234678045, 0.0], "isController": false}, {"data": ["IAM / Signup", 10, 0, 0.0, 343.9, 160, 476, 383.0, 473.2, 476.0, 476.0, 21.008403361344538, 5.377248555672269, 0.0], "isController": false}, {"data": ["IAM / List Organization", 10, 0, 0.0, 27.8, 21, 59, 24.5, 55.80000000000001, 59.0, 59.0, 7.9936051159072745, 268.33220923261393, 0.0], "isController": false}, {"data": ["Height / Get", 10, 0, 0.0, 21.1, 19, 24, 21.0, 24.0, 24.0, 24.0, 7.8431372549019605, 3.661151960784314, 0.0], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 0, 0.0, 20.4, 18, 27, 19.0, 26.5, 27.0, 27.0, 8.347245409015025, 7.417962228714525, 0.0], "isController": false}, {"data": ["Media / CreateMedia", 10, 0, 0.0, 23.499999999999996, 19, 38, 21.0, 37.300000000000004, 38.0, 38.0, 8.39630562552477, 4.157155226700252, 0.0], "isController": false}, {"data": ["IAM / List Users", 10, 0, 0.0, 43.6, 39, 65, 41.0, 63.10000000000001, 65.0, 65.0, 12.484394506866415, 50.68127730961298, 0.0], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 0, 0.0, 33.4, 23, 72, 24.5, 69.5, 72.0, 72.0, 9.689922480620154, 2.0818192829457365, 0.0], "isController": false}, {"data": ["BPM / Update", 10, 0, 0.0, 22.1, 21, 24, 22.0, 23.9, 24.0, 24.0, 7.77000777000777, 4.583090520590521, 0.0], "isController": false}, {"data": ["Properties Details", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 0.0, 0.0], "isController": false}, {"data": ["Heart / Get", 10, 0, 0.0, 20.7, 19, 24, 20.5, 23.8, 24.0, 24.0, 7.8064012490242, 3.773602166276347, 0.0], "isController": false}, {"data": ["Weight / Create", 10, 0, 0.0, 22.3, 21, 26, 22.0, 25.9, 26.0, 26.0, 7.727975270479135, 2.1508524922720245, 0.0], "isController": false}, {"data": ["Health- Vitals", 10, 0, 0.0, 417.0, 399, 450, 410.5, 449.4, 450.0, 450.0, 5.837711617046118, 90.44462383245767, 0.0], "isController": true}, {"data": ["CommunicationService/GetEmailTemplate (Cache)", 10, 0, 0.0, 20.2, 17, 35, 18.5, 33.60000000000001, 35.0, 35.0, 7.788161993769471, 2.3577443535825546, 0.0], "isController": false}, {"data": ["/media/download", 10, 0, 0.0, 9.700000000000001, 7, 17, 8.5, 16.8, 17.0, 17.0, 8.424599831508003, 67.94801758635215, 4.213122630581297], "isController": false}, {"data": ["CommunicationService/GetEmailTemplate", 10, 0, 0.0, 25.5, 18, 46, 23.0, 44.50000000000001, 46.0, 46.0, 8.097165991902834, 2.451290485829959, 0.0], "isController": false}, {"data": ["Logistics /  Add Device", 10, 0, 0.0, 27.100000000000005, 25, 33, 26.0, 32.6, 33.0, 33.0, 8.264462809917356, 6.763300619834711, 0.0], "isController": false}, {"data": ["Health / Create User Profile", 10, 0, 0.0, 30.1, 28, 35, 29.5, 34.7, 35.0, 35.0, 7.8125, 30.28106689453125, 0.0], "isController": false}, {"data": ["Health / Update User Profile", 10, 0, 0.0, 28.5, 25, 35, 27.5, 34.8, 35.0, 35.0, 7.77000777000777, 0.1593458624708625, 0.0], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 0, 0.0, 20.8, 19, 24, 20.0, 24.0, 24.0, 24.0, 8.4530853761623, 80.16397664835165, 0.0], "isController": false}, {"data": ["IAM / Change Password", 10, 0, 0.0, 230.9, 189, 322, 223.5, 317.90000000000003, 322.0, 322.0, 9.30232558139535, 4.016170058139535, 0.0], "isController": false}, {"data": ["Thread Setup", 10, 0, 0.0, 0.7, 0, 2, 0.5, 2.0, 2.0, 2.0, 12.285012285012284, 0.0, 0.0], "isController": false}, {"data": ["Logistics /  List Devices", 10, 0, 0.0, 21.5, 19, 27, 20.5, 26.700000000000003, 27.0, 27.0, 8.130081300813009, 86.85848577235772, 0.0], "isController": false}, {"data": ["CommunicationService/CreateNotificationEvent", 10, 0, 0.0, 22.700000000000003, 20, 28, 22.0, 28.0, 28.0, 28.0, 7.716049382716049, 2.667462384259259, 0.0], "isController": false}, {"data": ["IAM / Create Organization", 10, 0, 0.0, 53.8, 25, 133, 42.0, 129.4, 133.0, 133.0, 7.604562737642586, 7.6565470532319395, 0.0], "isController": false}, {"data": ["CommunicationService/GetNotificationEvent", 6, 0, 0.0, 19.5, 18, 23, 19.0, 23.0, 23.0, 23.0, 9.160305343511451, 4.651717557251908, 0.0], "isController": false}, {"data": ["CommunicationService/listSMSTemplates", 10, 0, 0.0, 20.6, 19, 24, 20.0, 24.0, 24.0, 24.0, 7.751937984496124, 28.835089631782946, 0.0], "isController": false}, {"data": ["AuditService / Event", 10, 0, 0.0, 28.5, 21, 49, 24.0, 47.800000000000004, 49.0, 49.0, 8.019246190858059, 0.16445719727345628, 0.0], "isController": false}, {"data": ["Audit", 10, 0, 0.0, 28.5, 21, 49, 24.0, 47.800000000000004, 49.0, 49.0, 8.019246190858059, 0.16445719727345628, 0.0], "isController": true}, {"data": ["Height / Create", 10, 0, 0.0, 22.0, 19, 26, 22.0, 25.700000000000003, 26.0, 26.0, 7.656967840735069, 2.2731623277182234, 0.0], "isController": false}, {"data": ["Weight / Get", 10, 0, 0.0, 25.4, 20, 35, 24.0, 34.7, 35.0, 35.0, 7.5075075075075075, 3.3651815878378377, 0.0], "isController": false}, {"data": ["/media/upload", 10, 0, 0.0, 13.4, 11, 23, 12.5, 22.1, 23.0, 23.0, 8.46740050804403, 4.7844120448772225, 70.12727561388654], "isController": false}, {"data": ["IAM / Login", 10, 0, 0.0, 130.8, 107, 191, 119.0, 190.3, 191.0, 191.0, 11.46788990825688, 3.528839951261468, 0.0], "isController": false}, {"data": ["Media / ListMedia", 10, 0, 0.0, 21.000000000000004, 19, 28, 20.0, 27.400000000000002, 28.0, 28.0, 7.942811755361397, 52.72196435663225, 0.0], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 0, 0.0, 19.1, 18, 20, 19.0, 20.0, 20.0, 20.0, 8.149959250203748, 4.648023634881826, 0.0], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 0, 0.0, 20.0, 19, 23, 20.0, 22.8, 23.0, 23.0, 8.460236886632826, 49.8526068104907, 0.0], "isController": false}, {"data": ["Media / UpdateMedia", 10, 0, 0.0, 21.9, 20, 29, 21.0, 28.400000000000002, 29.0, 29.0, 8.417508417508417, 5.540430345117845, 0.0], "isController": false}, {"data": ["IAM / Create Workspace", 10, 0, 0.0, 26.5, 22, 47, 24.0, 45.10000000000001, 47.0, 47.0, 8.032128514056224, 9.506777108433734, 0.0], "isController": false}, {"data": ["BPM / List", 10, 0, 0.0, 20.700000000000003, 20, 22, 21.0, 21.9, 22.0, 22.0, 7.788161993769471, 5.696614583333333, 0.0], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 747, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
