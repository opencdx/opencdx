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

    var data = {"OkPercent": 12.29946524064171, "KoPercent": 87.70053475935829};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.11200495049504951, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.1, 500, 1500, "Logistics /  List Countries"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Update Workspace"], "isController": false}, {"data": [0.1, 500, 1500, "Weight / Update"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Get User"], "isController": false}, {"data": [0.1, 500, 1500, "Height / List"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Update User"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Get TestCase"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Update Organization"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Update Device"], "isController": false}, {"data": [0.1, 500, 1500, "Heart / Create"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / GetOrganizationDetailsById"], "isController": false}, {"data": [0.1, 500, 1500, "Media / GetMedia"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/CreateSMSTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Get Device"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Current User"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Add Manufacturer"], "isController": false}, {"data": [0.1, 500, 1500, "Height / Update"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/UpdateSMSTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "Heart / List"], "isController": false}, {"data": [0.1, 500, 1500, "Connected Test /  Update Country"], "isController": false}, {"data": [0.1, 500, 1500, "Heart / Update"], "isController": false}, {"data": [0.1, 500, 1500, "BPM / Get"], "isController": false}, {"data": [0.1, 500, 1500, "Media"], "isController": true}, {"data": [0.1, 500, 1500, "Connected Test /  Get Country"], "isController": false}, {"data": [0.05, 500, 1500, "/iam/user/verify [GET]"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  List Manufacturers"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Update Vendor"], "isController": false}, {"data": [0.05, 500, 1500, "Logistics - Devices Vendor Manufacturers TestCases"], "isController": true}, {"data": [0.1, 500, 1500, "IAM / GetWorkspaceDetailsById"], "isController": false}, {"data": [0.0, 500, 1500, "IAM"], "isController": true}, {"data": [0.1, 500, 1500, "CommunicationService/UpdateEmailTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Update TestCase"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Add Vendor"], "isController": false}, {"data": [0.1, 500, 1500, "Health / Get User Profile"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/CreateEmailTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "Weight / List"], "isController": false}, {"data": [0.1, 500, 1500, "Health Profile"], "isController": true}, {"data": [0.1, 500, 1500, "BPM / Create"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Update Manufacturer"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Add TestCase"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/GetSMSTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/listEmailTemplates"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/GetSMSTemplate (Cache)"], "isController": false}, {"data": [0.5, 500, 1500, "IAM / Signup"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / List Organization"], "isController": false}, {"data": [0.1, 500, 1500, "Height / Get"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Get Manufacturer"], "isController": false}, {"data": [0.1, 500, 1500, "Media / CreateMedia"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / List Users"], "isController": false}, {"data": [0.1, 500, 1500, "Connected Test /  Add Country"], "isController": false}, {"data": [0.1, 500, 1500, "BPM / Update"], "isController": false}, {"data": [1.0, 500, 1500, "Properties Details"], "isController": false}, {"data": [0.1, 500, 1500, "Heart / Get"], "isController": false}, {"data": [0.1, 500, 1500, "Weight / Create"], "isController": false}, {"data": [0.05, 500, 1500, "Health- Vitals"], "isController": true}, {"data": [0.1, 500, 1500, "CommunicationService/GetEmailTemplate (Cache)"], "isController": false}, {"data": [0.1, 500, 1500, "/media/download"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/GetEmailTemplate"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Add Device"], "isController": false}, {"data": [0.1, 500, 1500, "Health / Create User Profile"], "isController": false}, {"data": [0.1, 500, 1500, "Health / Update User Profile"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  List Vendors"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Change Password"], "isController": false}, {"data": [1.0, 500, 1500, "Thread Setup"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  List Devices"], "isController": false}, {"data": [0.0, 500, 1500, "CommunicationService/CreateNotificationEvent"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Create Organization"], "isController": false}, {"data": [0.0, 500, 1500, "CommunicationService/GetNotificationEvent"], "isController": false}, {"data": [0.1, 500, 1500, "CommunicationService/listSMSTemplates"], "isController": false}, {"data": [0.1, 500, 1500, "AuditService / Event"], "isController": false}, {"data": [0.1, 500, 1500, "Audit"], "isController": true}, {"data": [0.1, 500, 1500, "Height / Create"], "isController": false}, {"data": [0.1, 500, 1500, "Weight / Get"], "isController": false}, {"data": [0.1, 500, 1500, "/media/upload"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Login"], "isController": false}, {"data": [0.1, 500, 1500, "Media / ListMedia"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  Get Vendor"], "isController": false}, {"data": [0.1, 500, 1500, "Logistics /  List Testcaes"], "isController": false}, {"data": [0.1, 500, 1500, "Media / UpdateMedia"], "isController": false}, {"data": [0.1, 500, 1500, "IAM / Create Workspace"], "isController": false}, {"data": [0.1, 500, 1500, "BPM / List"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 748, 656, 87.70053475935829, 43.86898395721925, 0, 889, 19.0, 43.10000000000002, 121.64999999999986, 803.7599999999993, 12.281421886544617, 1.3623772422214924, 1.3886091813069534], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Logistics /  List Countries", 10, 9, 90.0, 26.4, 17, 63, 21.0, 60.30000000000001, 63.0, 63.0, 7.309941520467836, 3.430104395102339, 0.0], "isController": false}, {"data": ["IAM / Update Workspace", 20, 18, 90.0, 20.15, 17, 31, 19.0, 29.300000000000015, 30.95, 31.0, 6.788866259334691, 0.9696012601832993, 0.0], "isController": false}, {"data": ["Weight / Update", 10, 9, 90.0, 20.4, 16, 34, 17.5, 33.400000000000006, 34.0, 34.0, 5.263157894736842, 0.32123766447368424, 0.0], "isController": false}, {"data": ["IAM / Get User", 10, 9, 90.0, 22.7, 20, 37, 20.5, 35.900000000000006, 37.0, 37.0, 8.944543828264758, 0.527588327370304, 0.0], "isController": false}, {"data": ["Height / List", 10, 9, 90.0, 19.4, 16, 38, 17.5, 36.00000000000001, 38.0, 38.0, 5.361930294906166, 0.4110464142091153, 0.0], "isController": false}, {"data": ["IAM / Update User", 10, 9, 90.0, 23.099999999999998, 19, 37, 22.0, 35.7, 37.0, 37.0, 9.541984732824428, 0.5628280057251909, 0.0], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 9, 90.0, 20.0, 18, 29, 18.0, 28.6, 29.0, 29.0, 7.315288953913679, 0.48863844184345284, 0.0], "isController": false}, {"data": ["IAM / Update Organization", 10, 9, 90.0, 21.0, 18, 36, 19.0, 34.50000000000001, 36.0, 36.0, 7.358351729212656, 0.8572767200147167, 0.0], "isController": false}, {"data": ["Logistics /  Update Device", 10, 9, 90.0, 40.9, 21, 71, 39.0, 70.10000000000001, 71.0, 71.0, 6.574621959237344, 0.6420529257067719, 0.0], "isController": false}, {"data": ["Heart / Create", 10, 9, 90.0, 19.9, 16, 45, 17.0, 42.50000000000001, 45.0, 45.0, 5.449591280653951, 0.25704615122615804, 0.0], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 9, 90.0, 27.1, 19, 54, 23.0, 52.00000000000001, 54.0, 54.0, 7.686395080707148, 1.0261037182936203, 0.0], "isController": false}, {"data": ["Media / GetMedia", 10, 9, 90.0, 21.7, 18, 34, 20.0, 33.0, 34.0, 34.0, 6.207324643078834, 0.5413223541278709, 0.0], "isController": false}, {"data": ["CommunicationService/CreateSMSTemplate", 10, 9, 90.0, 18.5, 15, 34, 17.0, 32.400000000000006, 34.0, 34.0, 5.4674685620557675, 0.15323862083105522, 0.0], "isController": false}, {"data": ["Logistics /  Get Device", 10, 9, 90.0, 34.300000000000004, 20, 73, 30.0, 70.5, 73.0, 73.0, 7.262164124909224, 0.8269222040668119, 0.0], "isController": false}, {"data": ["IAM / Current User", 10, 9, 90.0, 27.1, 22, 57, 23.0, 54.10000000000001, 57.0, 57.0, 10.53740779768177, 0.6143391069546892, 0.0], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 9, 90.0, 24.5, 17, 64, 19.0, 60.70000000000001, 64.0, 64.0, 7.251631617113851, 0.6415994379985497, 0.0], "isController": false}, {"data": ["Height / Update", 10, 9, 90.0, 28.000000000000004, 16, 92, 19.5, 86.50000000000003, 92.0, 92.0, 5.219206680584551, 0.23751467901878914, 0.0], "isController": false}, {"data": ["CommunicationService/UpdateSMSTemplate", 10, 9, 90.0, 20.1, 16, 25, 20.0, 24.9, 25.0, 25.0, 5.730659025787966, 0.16397295845272206, 0.0], "isController": false}, {"data": ["Heart / List", 10, 9, 90.0, 24.6, 17, 34, 26.0, 33.6, 34.0, 34.0, 5.420054200542006, 0.42661754742547425, 0.0], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 9, 90.0, 21.099999999999998, 19, 28, 20.0, 27.6, 28.0, 28.0, 7.518796992481203, 0.27901785714285715, 0.0], "isController": false}, {"data": ["Heart / Update", 10, 9, 90.0, 20.1, 16, 38, 18.0, 36.400000000000006, 38.0, 38.0, 5.417118093174431, 0.3496792050379198, 0.0], "isController": false}, {"data": ["BPM / Get", 10, 9, 90.0, 18.0, 15, 35, 16.0, 33.2, 35.0, 35.0, 5.257623554153523, 0.3912411277602524, 0.0], "isController": false}, {"data": ["Media", 10, 9, 90.0, 202.0, 90, 402, 136.5, 401.6, 402.0, 402.0, 5.2273915316257185, 9.24492779665447, 43.07646285284893], "isController": true}, {"data": ["Connected Test /  Get Country", 10, 9, 90.0, 23.0, 19, 43, 20.0, 41.300000000000004, 43.0, 43.0, 7.288629737609329, 0.39005557580174927, 0.0], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 9, 90.0, 562.6, 554, 640, 554.0, 631.4000000000001, 640.0, 640.0, 15.625, 7.9193115234375, 3.387451171875], "isController": false}, {"data": ["Logistics /  List Manufacturers", 10, 9, 90.0, 20.7, 17, 35, 18.5, 33.900000000000006, 35.0, 35.0, 7.390983000739098, 0.25262148928307465, 0.0], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 9, 90.0, 20.9, 18, 29, 20.0, 28.300000000000004, 29.0, 29.0, 7.2727272727272725, 0.6242897727272727, 0.0], "isController": false}, {"data": ["Logistics - Devices Vendor Manufacturers TestCases", 10, 9, 90.0, 402.3, 324, 718, 381.5, 686.3000000000002, 718.0, 718.0, 6.006006006006006, 9.49582394894895, 0.0], "isController": true}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 9, 90.0, 19.7, 18, 28, 19.0, 27.200000000000003, 28.0, 28.0, 7.547169811320755, 1.1409198113207548, 0.0], "isController": false}, {"data": ["IAM", 10, 9, 90.0, 1883.1000000000001, 1515, 2758, 1815.5, 2690.8, 2758.0, 2758.0, 2.9437739181630853, 7.856771599941125, 0.6382009861642626], "isController": true}, {"data": ["CommunicationService/UpdateEmailTemplate", 10, 9, 90.0, 18.0, 15, 25, 17.0, 24.6, 25.0, 25.0, 5.662514156285391, 0.171423768403171, 0.0], "isController": false}, {"data": ["Logistics /  Update TestCase", 10, 9, 90.0, 26.4, 18, 66, 21.0, 62.500000000000014, 66.0, 66.0, 6.954102920723227, 0.3687576060500696, 0.0], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 9, 90.0, 20.7, 17, 38, 18.5, 36.50000000000001, 38.0, 38.0, 7.423904974016332, 0.42049461766889384, 0.0], "isController": false}, {"data": ["Health / Get User Profile", 10, 9, 90.0, 21.9, 17, 50, 18.5, 47.30000000000001, 50.0, 50.0, 5.307855626326964, 2.141284335191083, 0.0], "isController": false}, {"data": ["CommunicationService/CreateEmailTemplate", 10, 9, 90.0, 54.099999999999994, 16, 202, 18.0, 193.8, 202.0, 202.0, 5.521811154058531, 0.1650072473771397, 0.0], "isController": false}, {"data": ["Weight / List", 10, 9, 90.0, 18.6, 16, 32, 17.0, 30.700000000000003, 32.0, 32.0, 5.238344683080147, 0.3938989654269251, 0.0], "isController": false}, {"data": ["Health Profile", 10, 9, 90.0, 118.89999999999999, 53, 345, 62.0, 335.90000000000003, 345.0, 345.0, 4.926108374384237, 3.9990955972906406, 0.0], "isController": true}, {"data": ["BPM / Create", 10, 9, 90.0, 20.1, 16, 43, 17.5, 40.80000000000001, 43.0, 43.0, 5.449591280653951, 0.31292574931880107, 0.0], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 9, 90.0, 23.3, 17, 49, 20.5, 46.900000000000006, 49.0, 49.0, 7.530120481927711, 0.6669745387801205, 0.0], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 9, 90.0, 21.3, 17, 46, 18.5, 43.50000000000001, 46.0, 46.0, 7.256894049346879, 0.3670967888243832, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate", 10, 9, 90.0, 20.8, 17, 30, 17.5, 29.9, 30.0, 30.0, 5.458515283842795, 0.24147533433406113, 0.0], "isController": false}, {"data": ["CommunicationService/listEmailTemplates", 10, 9, 90.0, 20.9, 16, 36, 19.5, 34.800000000000004, 36.0, 36.0, 5.599104143337066, 0.3745494470884658, 0.0], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate (Cache)", 10, 9, 90.0, 19.9, 17, 27, 18.0, 26.700000000000003, 27.0, 27.0, 5.727376861397479, 0.253369308419244, 0.0], "isController": false}, {"data": ["IAM / Signup", 10, 0, 0.0, 817.7, 600, 889, 877.0, 888.8, 889.0, 889.0, 11.248593925759279, 2.879156706974128, 0.0], "isController": false}, {"data": ["IAM / List Organization", 10, 9, 90.0, 19.8, 18, 28, 19.0, 27.200000000000003, 28.0, 28.0, 7.547169811320755, 1.0097287735849056, 0.0], "isController": false}, {"data": ["Height / Get", 10, 9, 90.0, 19.3, 17, 34, 17.5, 32.50000000000001, 34.0, 34.0, 4.940711462450593, 0.30879446640316205, 0.0], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 9, 90.0, 20.1, 18, 33, 19.0, 31.600000000000005, 33.0, 33.0, 7.501875468867216, 0.7853525881470368, 0.0], "isController": false}, {"data": ["Media / CreateMedia", 10, 9, 90.0, 72.4, 18, 208, 28.5, 208.0, 208.0, 208.0, 6.5274151436031325, 0.4264492901436031, 0.0], "isController": false}, {"data": ["IAM / List Users", 10, 9, 90.0, 31.4, 22, 82, 26.5, 76.80000000000001, 82.0, 82.0, 9.1324200913242, 3.8420376712328768, 0.0], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 9, 90.0, 76.10000000000001, 21, 245, 25.0, 239.8, 245.0, 245.0, 7.468259895444362, 0.27860110156833456, 0.0], "isController": false}, {"data": ["BPM / Update", 10, 9, 90.0, 19.1, 16, 40, 17.0, 37.900000000000006, 40.0, 40.0, 5.2246603970741905, 0.3908290882967607, 0.0], "isController": false}, {"data": ["Properties Details", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 0.0, 0.0], "isController": false}, {"data": ["Heart / Get", 10, 9, 90.0, 18.799999999999997, 16, 41, 16.0, 38.60000000000001, 41.0, 41.0, 5.446623093681917, 0.34945618872549017, 0.0], "isController": false}, {"data": ["Weight / Create", 10, 9, 90.0, 26.3, 16, 62, 18.0, 59.900000000000006, 62.0, 62.0, 5.307855626326964, 0.23170033837579618, 0.0], "isController": false}, {"data": ["Health- Vitals", 10, 9, 90.0, 337.9, 282, 678, 297.0, 645.5000000000001, 678.0, 678.0, 4.844961240310077, 4.94621335998062, 0.0], "isController": true}, {"data": ["CommunicationService/GetEmailTemplate (Cache)", 10, 9, 90.0, 18.400000000000002, 16, 25, 17.0, 24.8, 25.0, 25.0, 5.817335660267597, 0.26814281559045955, 0.0], "isController": false}, {"data": ["/media/download", 10, 9, 90.0, 5.699999999999999, 2, 18, 3.0, 17.8, 18.0, 18.0, 6.207324643078834, 6.124883612662942, 1.3578522656734948], "isController": false}, {"data": ["CommunicationService/GetEmailTemplate", 10, 9, 90.0, 29.0, 16, 62, 20.5, 61.400000000000006, 62.0, 62.0, 5.770340450086556, 0.2659766301211771, 0.0], "isController": false}, {"data": ["Logistics /  Add Device", 10, 9, 90.0, 27.199999999999996, 17, 64, 20.5, 62.10000000000001, 64.0, 64.0, 6.997900629811057, 0.683388733379986, 0.0], "isController": false}, {"data": ["Health / Create User Profile", 10, 9, 90.0, 72.7, 18, 235, 21.5, 232.5, 235.0, 235.0, 5.050505050505051, 1.972360321969697, 0.0], "isController": false}, {"data": ["Health / Update User Profile", 10, 9, 90.0, 24.3, 18, 60, 20.5, 56.500000000000014, 60.0, 60.0, 4.935834155972359, 0.08820875493583416, 0.0], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 9, 90.0, 17.9, 16, 26, 17.0, 25.200000000000003, 26.0, 26.0, 7.423904974016332, 0.24939680772086117, 0.0], "isController": false}, {"data": ["IAM / Change Password", 10, 9, 90.0, 44.8, 19, 222, 22.5, 205.10000000000005, 222.0, 222.0, 8.123476848090982, 0.4791582047116165, 0.0], "isController": false}, {"data": ["Thread Setup", 10, 0, 0.0, 0.7, 0, 3, 0.5, 2.8000000000000007, 3.0, 3.0, 12.391573729863694, 0.0, 0.0], "isController": false}, {"data": ["Logistics /  List Devices", 10, 9, 90.0, 19.499999999999996, 17, 35, 17.5, 33.60000000000001, 35.0, 35.0, 7.541478129713424, 0.2526100584464555, 0.0], "isController": false}, {"data": ["CommunicationService/CreateNotificationEvent", 9, 9, 100.0, 21.000000000000004, 16, 44, 17.0, 44.0, 44.0, 44.0, 11.952191235059761, 0.21009711155378485, 0.0], "isController": false}, {"data": ["IAM / Create Organization", 10, 9, 90.0, 24.5, 18, 70, 20.0, 65.00000000000001, 70.0, 70.0, 7.336757153338224, 0.8547608675715334, 0.0], "isController": false}, {"data": ["CommunicationService/GetNotificationEvent", 8, 8, 100.0, 19.125, 16, 37, 17.0, 37.0, 37.0, 37.0, 11.940298507462687, 0.20988805970149252, 0.0], "isController": false}, {"data": ["CommunicationService/listSMSTemplates", 10, 9, 90.0, 17.8, 15, 34, 16.0, 32.300000000000004, 34.0, 34.0, 5.515719801434087, 0.18852557915057916, 0.0], "isController": false}, {"data": ["AuditService / Event", 10, 9, 90.0, 64.4, 19, 207, 42.5, 198.70000000000005, 207.0, 207.0, 7.142857142857142, 0.12765066964285715, 0.0], "isController": false}, {"data": ["Audit", 10, 9, 90.0, 64.4, 19, 207, 42.5, 198.70000000000005, 207.0, 207.0, 7.142857142857142, 0.12765066964285715, 0.0], "isController": true}, {"data": ["Height / Create", 10, 9, 90.0, 22.7, 17, 52, 18.0, 49.30000000000001, 52.0, 52.0, 4.866180048661801, 0.22144920924574207, 0.0], "isController": false}, {"data": ["Weight / Get", 10, 9, 90.0, 25.2, 16, 75, 18.0, 70.70000000000002, 75.0, 75.0, 5.443658138268916, 0.33012809608056615, 0.0], "isController": false}, {"data": ["/media/upload", 10, 9, 90.0, 59.699999999999996, 9, 133, 42.0, 132.5, 133.0, 133.0, 7.24112961622013, 3.7181503439536567, 58.086729498551776], "isController": false}, {"data": ["IAM / Login", 10, 9, 90.0, 57.6, 31, 213, 38.0, 199.60000000000005, 213.0, 213.0, 10.16260162601626, 0.5180544969512195, 0.0], "isController": false}, {"data": ["Media / ListMedia", 10, 9, 90.0, 22.1, 17, 51, 17.5, 49.00000000000001, 51.0, 51.0, 6.195786864931847, 0.2117700588599752, 0.0], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 9, 90.0, 19.3, 17, 29, 18.0, 28.1, 29.0, 29.0, 7.575757575757576, 0.5519057765151515, 0.0], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 9, 90.0, 18.900000000000006, 16, 29, 17.0, 28.300000000000004, 29.0, 29.0, 7.1377587437544605, 0.24187522305496073, 0.0], "isController": false}, {"data": ["Media / UpdateMedia", 10, 9, 90.0, 20.4, 17, 33, 18.0, 32.300000000000004, 33.0, 33.0, 6.422607578676943, 0.5243456968529223, 0.0], "isController": false}, {"data": ["IAM / Create Workspace", 10, 9, 90.0, 23.5, 18, 52, 19.5, 49.20000000000001, 52.0, 52.0, 7.44047619047619, 0.9983607700892857, 0.0], "isController": false}, {"data": ["BPM / List", 10, 9, 90.0, 17.4, 16, 30, 16.0, 28.600000000000005, 30.0, 30.0, 5.571030640668524, 0.4956258704735376, 0.0], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": [" 500/ 13 INTERNAL", 620, 94.51219512195122, 82.88770053475936], "isController": false}, {"data": ["401/Unauthorized", 18, 2.7439024390243905, 2.406417112299465], "isController": false}, {"data": ["404/Not Found", 9, 1.3719512195121952, 1.2032085561497325], "isController": false}, {"data": [" 500/ 9 FAILED_PRECONDITION", 9, 1.3719512195121952, 1.2032085561497325], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 748, 656, " 500/ 13 INTERNAL", 620, "401/Unauthorized", 18, "404/Not Found", 9, " 500/ 9 FAILED_PRECONDITION", 9, "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["Logistics /  List Countries", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Update Workspace", 20, 18, " 500/ 13 INTERNAL", 18, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Weight / Update", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Get User", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Height / List", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Update User", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Get TestCase", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Update Organization", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Update Device", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Heart / Create", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / GetOrganizationDetailsById", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Media / GetMedia", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/CreateSMSTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Get Device", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Current User", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Add Manufacturer", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Height / Update", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/UpdateSMSTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Heart / List", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Connected Test /  Update Country", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Heart / Update", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["BPM / Get", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["Connected Test /  Get Country", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["/iam/user/verify [GET]", 10, 9, "401/Unauthorized", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  List Manufacturers", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Update Vendor", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["IAM / GetWorkspaceDetailsById", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["CommunicationService/UpdateEmailTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Update TestCase", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Add Vendor", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Health / Get User Profile", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/CreateEmailTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Weight / List", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["BPM / Create", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Update Manufacturer", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Add TestCase", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/listEmailTemplates", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/GetSMSTemplate (Cache)", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["IAM / List Organization", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Height / Get", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Get Manufacturer", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Media / CreateMedia", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / List Users", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Connected Test /  Add Country", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["BPM / Update", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["Heart / Get", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Weight / Create", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["CommunicationService/GetEmailTemplate (Cache)", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["/media/download", 10, 9, "404/Not Found", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/GetEmailTemplate", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Add Device", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Health / Create User Profile", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Health / Update User Profile", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  List Vendors", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Change Password", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["Logistics /  List Devices", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/CreateNotificationEvent", 9, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Create Organization", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/GetNotificationEvent", 8, 8, " 500/ 13 INTERNAL", 8, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["CommunicationService/listSMSTemplates", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["AuditService / Event", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["Height / Create", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Weight / Get", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["/media/upload", 10, 9, "401/Unauthorized", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Login", 10, 9, " 500/ 9 FAILED_PRECONDITION", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Media / ListMedia", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  Get Vendor", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Logistics /  List Testcaes", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Media / UpdateMedia", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["IAM / Create Workspace", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["BPM / List", 10, 9, " 500/ 13 INTERNAL", 9, "", "", "", "", "", "", "", ""], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
