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
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 64.0, "series": [{"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1100.0, 3.0], [1300.0, 1.0], [1500.0, 1.0], [1000.0, 11.0]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[0.0, 15.0], [100.0, 1.0]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[100.0, 15.0], [200.0, 2.0]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[300.0, 1.0], [100.0, 15.0]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[0.0, 4.0], [200.0, 1.0], [100.0, 11.0]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[100.0, 14.0], [500.0, 2.0]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[300.0, 1.0], [100.0, 14.0], [400.0, 1.0]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[100.0, 16.0], [200.0, 1.0]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[0.0, 34.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[0.0, 2.0], [100.0, 15.0]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[0.0, 10.0], [100.0, 6.0]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[300.0, 1.0], [100.0, 14.0], [200.0, 1.0]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[0.0, 34.0]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[0.0, 11.0], [100.0, 5.0]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[0.0, 15.0], [100.0, 1.0]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[0.0, 13.0], [200.0, 1.0], [100.0, 3.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[0.0, 1.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[100.0, 17.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[0.0, 64.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[0.0, 17.0]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[200.0, 1.0], [100.0, 15.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[0.0, 15.0], [100.0, 2.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[0.0, 15.0], [100.0, 1.0]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1500.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 4421.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 4421.0, "series": [{"data": [[0.0, 4421.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 4.9E-324, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 1.0, "minX": 1.70593926E12, "maxY": 1.0, "series": [{"data": [[1.70593926E12, 1.0]], "isOverall": false, "label": "Setup Details", "isController": false}, {"data": [[1.70593926E12, 1.0], [1.70593932E12, 1.0]], "isOverall": false, "label": "Integration Test", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70593932E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var timeVsThreadsInfos = {
        data: {"result": {"minY": 0.9411764705882352, "minX": 1.0, "maxY": 1114.4375, "series": [{"data": [[1.0, 4.6875]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.0, 4.6875]], "isOverall": false, "label": "/connected-test/vendor [GET]-Aggregated", "isController": false}, {"data": [[1.0, 5.062500000000001]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.0, 5.062500000000001]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]-Aggregated", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "/connected-test/device [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 5.859375000000002]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.0, 5.859375000000002]], "isOverall": false, "label": "/communications/email [POST]-Aggregated", "isController": false}, {"data": [[1.0, 5.109375]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.0, 5.109375]], "isOverall": false, "label": "/communications/email [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 4.734375000000002]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.0, 4.734375000000002]], "isOverall": false, "label": "/communications/sms [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 6.1875]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.0, 6.1875]], "isOverall": false, "label": "Connected Test /  Update Device-Aggregated", "isController": false}, {"data": [[1.0, 8.375000000000002]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.0, 8.375000000000002]], "isOverall": false, "label": "/event/audit-Aggregated", "isController": false}, {"data": [[1.0, 2.71875]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.0, 2.71875]], "isOverall": false, "label": "/communications/email [GET] (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 6.671875]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.0, 6.671875]], "isOverall": false, "label": "/communications/event/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 4.8125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.0, 4.8125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 6.828125000000001]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.0, 6.828125000000001]], "isOverall": false, "label": "CommunicationService/SendNotification-Aggregated", "isController": false}, {"data": [[1.0, 17.500000000000004]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.0, 17.500000000000004]], "isOverall": false, "label": "Classification-Aggregated", "isController": true}, {"data": [[1.0, 5.875]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.0, 5.875]], "isOverall": false, "label": "Connected Test /  Update Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 6.294117647058823]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.0, 6.294117647058823]], "isOverall": false, "label": "IAM / Current User-Aggregated", "isController": false}, {"data": [[1.0, 12.437499999999998]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.0, 12.437499999999998]], "isOverall": false, "label": "Protector / DetectAnomalies-Aggregated", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity-Aggregated", "isController": false}, {"data": [[1.0, 8.588235294117647]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.0, 8.588235294117647]], "isOverall": false, "label": "/iam/user [GET]]-Aggregated", "isController": false}, {"data": [[1.0, 6.0625]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.0, 6.0625]], "isOverall": false, "label": "Connected Test /  Update TestCase-Aggregated", "isController": false}, {"data": [[1.0, 2.8593750000000004]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.0, 2.8593750000000004]], "isOverall": false, "label": "/communications/event [GET] (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 7.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.0, 7.0]], "isOverall": false, "label": "/media [PUT]]-Aggregated", "isController": false}, {"data": [[1.0, 7.874999999999999]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.0, 7.874999999999999]], "isOverall": false, "label": "/connected-test/testcase [POST]-Aggregated", "isController": false}, {"data": [[1.0, 6.249999999999999]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.0, 6.249999999999999]], "isOverall": false, "label": "Protector / ProtectPrivacy-Aggregated", "isController": false}, {"data": [[1.0, 6.625]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.0, 6.625]], "isOverall": false, "label": "Connected Test /  Delete Device-Aggregated", "isController": false}, {"data": [[1.0, 4.859375000000001]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.0, 4.859375000000001]], "isOverall": false, "label": "/communications/sms [POST]-Aggregated", "isController": false}, {"data": [[1.0, 7.0625]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.0, 7.0625]], "isOverall": false, "label": "/connected-test/manufacturer [POST]-Aggregated", "isController": false}, {"data": [[1.0, 5.7058823529411775]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.0, 5.7058823529411775]], "isOverall": false, "label": "Connected Test /  Get Country-Aggregated", "isController": false}, {"data": [[1.0, 6.046875000000001]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.0, 6.046875000000001]], "isOverall": false, "label": "/communications/email/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 5.500000000000001]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.0, 5.500000000000001]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/iam/workspace [POST]-Aggregated", "isController": false}, {"data": [[1.0, 10.75]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.0, 10.75]], "isOverall": false, "label": "/connected-test [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 7.125]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.0, 7.125]], "isOverall": false, "label": "Connected Test /  ListConnectedTests-Aggregated", "isController": false}, {"data": [[1.0, 5.9411764705882355]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.0, 5.9411764705882355]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 1114.4375]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.0, 1114.4375]], "isOverall": false, "label": "IAM-Aggregated", "isController": true}, {"data": [[1.0, 6.75]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.0, 6.75]], "isOverall": false, "label": "/routine/medication [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "Routine / CreateLabResult-Aggregated", "isController": false}, {"data": [[1.0, 6.437500000000001]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.0, 6.437500000000001]], "isOverall": false, "label": "/connected-test/testcase [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 7.500000000000001]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.0, 7.500000000000001]], "isOverall": false, "label": "Connected Test /  Add TestCase-Aggregated", "isController": false}, {"data": [[1.0, 8.176470588235295]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.0, 8.176470588235295]], "isOverall": false, "label": "/iam/organization [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 4.249999999999998]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.0, 4.249999999999998]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 3.421875]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.0, 3.421875]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 6.500000000000001]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.0, 6.500000000000001]], "isOverall": false, "label": "/connected-test [GET]-Aggregated", "isController": false}, {"data": [[1.0, 7.187500000000001]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.0, 7.187500000000001]], "isOverall": false, "label": "Routine / CreateDeliveryTracking-Aggregated", "isController": false}, {"data": [[1.0, 27.625]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.0, 27.625]], "isOverall": false, "label": "Predictor-Aggregated", "isController": true}, {"data": [[1.0, 9.764705882352942]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.0, 9.764705882352942]], "isOverall": false, "label": "/connected-test/country [POST]-Aggregated", "isController": false}, {"data": [[1.0, 7.750000000000002]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.0, 7.750000000000002]], "isOverall": false, "label": "Routine / CreateLabOrder-Aggregated", "isController": false}, {"data": [[1.0, 5.0588235294117645]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.0, 5.0588235294117645]], "isOverall": false, "label": "/iam/user/current [GET]-Aggregated", "isController": false}, {"data": [[1.0, 5.588235294117647]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.0, 5.588235294117647]], "isOverall": false, "label": "/connected-test/country [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 53.43750000000001]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.0, 53.43750000000001]], "isOverall": false, "label": "Connected Test - Delete-Aggregated", "isController": true}, {"data": [[1.0, 6.5625]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.0, 6.5625]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis-Aggregated", "isController": false}, {"data": [[1.0, 8.75]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.0, 8.75]], "isOverall": false, "label": "Media / CreateMedia-Aggregated", "isController": false}, {"data": [[1.0, 6.9375]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.0, 6.9375]], "isOverall": false, "label": "/routine/diagnosis [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.75]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.0, 5.75]], "isOverall": false, "label": "/protector/authorize [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 11.000000000000004]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.0, 11.000000000000004]], "isOverall": false, "label": "Connected Test /  Add Country-Aggregated", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 10.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.0, 10.0]], "isOverall": false, "label": "Routine / CreateRoutine-Aggregated", "isController": false}, {"data": [[1.0, 15.000000000000002]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.0, 15.000000000000002]], "isOverall": false, "label": "Predictor / Predict-Aggregated", "isController": false}, {"data": [[1.0, 7.312499999999999]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.0, 7.312499999999999]], "isOverall": false, "label": "Connected Test /  Add Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 173.2941176470588]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.0, 173.2941176470588]], "isOverall": false, "label": "/iam/user/login {POST]-Aggregated", "isController": false}, {"data": [[1.0, 5.031249999999999]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.0, 5.031249999999999]], "isOverall": false, "label": "/media/download-Aggregated", "isController": false}, {"data": [[1.0, 4.875000000000002]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.0, 4.875000000000002]], "isOverall": false, "label": "Connected Test /  Get Device-Aggregated", "isController": false}, {"data": [[1.0, 4.375]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.0, 4.375]], "isOverall": false, "label": "/connected-test/device [GET]-Aggregated", "isController": false}, {"data": [[1.0, 156.75]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.0, 156.75]], "isOverall": false, "label": "Communications Low-Aggregated", "isController": true}, {"data": [[1.0, 0.9411764705882352]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.0, 0.9411764705882352]], "isOverall": false, "label": "Thread Setup-Aggregated", "isController": false}, {"data": [[1.0, 8.764705882352942]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.0, 8.764705882352942]], "isOverall": false, "label": "/iam/organization [POST]-Aggregated", "isController": false}, {"data": [[1.0, 6.515624999999999]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.0, 6.515624999999999]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 3.25]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.0, 3.25]], "isOverall": false, "label": "/communications/sms [DELETE}-Aggregated", "isController": false}, {"data": [[1.0, 8.875000000000002]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.0, 8.875000000000002]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl-Aggregated", "isController": false}, {"data": [[1.0, 125.00000000000001]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.0, 125.00000000000001]], "isOverall": false, "label": "Routine-Aggregated", "isController": true}, {"data": [[1.0, 12.937499999999998]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.0, 12.937499999999998]], "isOverall": false, "label": "/iam/workspace/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 9.250000000000002]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.0, 9.250000000000002]], "isOverall": false, "label": "IAM / Get User Profile-Aggregated", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "AuditService / Event-Aggregated", "isController": false}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Audit-Aggregated", "isController": true}, {"data": [[1.0, 10.6875]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.0, 10.6875]], "isOverall": false, "label": "ANF / GetANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 200.25]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.0, 200.25]], "isOverall": false, "label": "Communications Immediate-Aggregated", "isController": true}, {"data": [[1.0, 10.5625]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.0, 10.5625]], "isOverall": false, "label": "/media/upload-Aggregated", "isController": false}, {"data": [[1.0, 5.875000000000001]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.0, 5.875000000000001]], "isOverall": false, "label": "Connected Test /  Add Vendor-Aggregated", "isController": false}, {"data": [[1.0, 7.374999999999999]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.0, 7.374999999999999]], "isOverall": false, "label": "Media / ListMedia-Aggregated", "isController": false}, {"data": [[1.0, 170.0]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.0, 170.0]], "isOverall": false, "label": "Communications High-Aggregated", "isController": true}, {"data": [[1.0, 5.874999999999999]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.0, 5.874999999999999]], "isOverall": false, "label": "/connected-test/testcase [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 9.937499999999998]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.0, 9.937499999999998]], "isOverall": false, "label": "/anf [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 5.437500000000001]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.0, 5.437500000000001]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 8.176470588235293]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.0, 8.176470588235293]], "isOverall": false, "label": "IAM / Create Workspace-Aggregated", "isController": false}, {"data": [[1.0, 177.99999999999997]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.0, 177.99999999999997]], "isOverall": false, "label": "/iam/user/password [POST]-Aggregated", "isController": false}, {"data": [[1.0, 51.1875]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.0, 51.1875]], "isOverall": false, "label": "Connected Test - Connected Tests-Aggregated", "isController": true}, {"data": [[1.0, 5.828125]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.0, 5.828125]], "isOverall": false, "label": "/communications/sms/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 4.874999999999999]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.0, 4.874999999999999]], "isOverall": false, "label": "Connected Test /  Delete Country-Aggregated", "isController": false}, {"data": [[1.0, 7.624999999999999]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.0, 7.624999999999999]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 15.4375]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.0, 15.4375]], "isOverall": false, "label": "ANF / CreateANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 11.441176470588234]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.0, 11.441176470588234]], "isOverall": false, "label": "IAM / Update Workspace-Aggregated", "isController": false}, {"data": [[1.0, 14.187499999999998]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.0, 14.187499999999998]], "isOverall": false, "label": "IAM / Update User Profile-Aggregated", "isController": false}, {"data": [[1.0, 6.823529411764705]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.0, 6.823529411764705]], "isOverall": false, "label": "IAM / Get User-Aggregated", "isController": false}, {"data": [[1.0, 10.117647058823529]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.0, 10.117647058823529]], "isOverall": false, "label": "/iam/user [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 11.125]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.0, 11.125]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 7.5625]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.0, 7.5625]], "isOverall": false, "label": "/connected-test/list [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 10.705882352941176]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.0, 10.705882352941176]], "isOverall": false, "label": "IAM / Update User-Aggregated", "isController": false}, {"data": [[1.0, 6.875000000000001]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.0, 6.875000000000001]], "isOverall": false, "label": "/media/list  [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 8.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.0, 8.0]], "isOverall": false, "label": "IAM / Update Organization-Aggregated", "isController": false}, {"data": [[1.0, 5.76470588235294]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.0, 5.76470588235294]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 4.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.0, 4.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 106.58823529411765]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.0, 106.58823529411765]], "isOverall": false, "label": "/iam/user/signup {POST]-Aggregated", "isController": false}, {"data": [[1.0, 3.640625]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.0, 3.640625]], "isOverall": false, "label": "/communications/email [GET]-Aggregated", "isController": false}, {"data": [[1.0, 6.937499999999999]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.0, 6.937499999999999]], "isOverall": false, "label": "Media / GetMedia-Aggregated", "isController": false}, {"data": [[1.0, 5.156250000000001]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.0, 5.156250000000001]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 5.8125]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.0, 5.8125]], "isOverall": false, "label": "/media  [DELETE]]-Aggregated", "isController": false}, {"data": [[1.0, 9.875]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.0, 9.875]], "isOverall": false, "label": "/routine [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.218749999999999]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.0, 5.218749999999999]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 12.874999999999998]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.0, 12.874999999999998]], "isOverall": false, "label": "/anf [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 6.0624999999999964]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.0, 6.0624999999999964]], "isOverall": false, "label": "/communications/notification [POST]-Aggregated", "isController": false}, {"data": [[1.0, 8.375]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.0, 8.375]], "isOverall": false, "label": "/classify [POST]-Aggregated", "isController": false}, {"data": [[1.0, 6.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.0, 6.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 7.312500000000001]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.0, 7.312500000000001]], "isOverall": false, "label": "/iam/workspace [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 8.176470588235293]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.0, 8.176470588235293]], "isOverall": false, "label": "Connected Test /  Update Country-Aggregated", "isController": false}, {"data": [[1.0, 5.062499999999999]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.0, 5.062499999999999]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 3.7812500000000013]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.0, 3.7812500000000013]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 101.4375]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.0, 101.4375]], "isOverall": false, "label": "Media-Aggregated", "isController": true}, {"data": [[1.0, 151.75]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.0, 151.75]], "isOverall": false, "label": "Communications Medium-Aggregated", "isController": true}, {"data": [[1.0, 6.125]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.0, 6.125]], "isOverall": false, "label": "/media [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 20.1875]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.0, 20.1875]], "isOverall": false, "label": "/routine/labOrder [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 7.749999999999999]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.0, 7.749999999999999]], "isOverall": false, "label": "Media / DeleteMedia-Aggregated", "isController": false}, {"data": [[1.0, 5.5]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.0, 5.5]], "isOverall": false, "label": "/connected-test/device [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 9.23529411764706]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.0, 9.23529411764706]], "isOverall": false, "label": "/iam/user/verify [GET]-Aggregated", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "Connected Test /  Get Vendor-Aggregated", "isController": false}, {"data": [[1.0, 5.53125]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.0, 5.53125]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 4.75]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.0, 4.75]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 6.453124999999999]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.0, 6.453124999999999]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 6.687500000000001]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.0, 6.687500000000001]], "isOverall": false, "label": "/connected-test/device [POST]-Aggregated", "isController": false}, {"data": [[1.0, 84.25]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.0, 84.25]], "isOverall": false, "label": "ANF-Aggregated", "isController": true}, {"data": [[1.0, 6.515625000000003]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.0, 6.515625000000003]], "isOverall": false, "label": "CommunicationService/listNotificationEvents-Aggregated", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.0, 6.562500000000001]], "isOverall": false, "label": "Connected Test /  Delete TestCase-Aggregated", "isController": false}, {"data": [[1.0, 6.71875]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.0, 6.71875]], "isOverall": false, "label": "CommunicationService/listEmailTemplates-Aggregated", "isController": false}, {"data": [[1.0, 46.31249999999999]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.0, 46.31249999999999]], "isOverall": false, "label": "IAM Profile-Aggregated", "isController": true}, {"data": [[1.0, 6.4375]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.0, 6.4375]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 99.88235294117648]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.0, 99.88235294117648]], "isOverall": false, "label": "IAM / Signup-Aggregated", "isController": false}, {"data": [[1.0, 4.812499999999999]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.0, 4.812499999999999]], "isOverall": false, "label": "Connected Test /  Get TestCase-Aggregated", "isController": false}, {"data": [[1.0, 17.176470588235293]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.0, 17.176470588235293]], "isOverall": false, "label": "IAM / List Organization-Aggregated", "isController": false}, {"data": [[1.0, 4.859374999999997]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.0, 4.859374999999997]], "isOverall": false, "label": "/communications/event [PUT]]-Aggregated", "isController": false}, {"data": [[1.0, 4.6875]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.0, 4.6875]], "isOverall": false, "label": "/connected-test/testcase [GET]-Aggregated", "isController": false}, {"data": [[1.0, 6.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.0, 6.0]], "isOverall": false, "label": "Connected Test /  Update Vendor-Aggregated", "isController": false}, {"data": [[1.0, 7.812500000000001]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.0, 7.812500000000001]], "isOverall": false, "label": "/anf [GET]-Aggregated", "isController": false}, {"data": [[1.0, 12.625]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.0, 12.625]], "isOverall": false, "label": "/predictor [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 7.375]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.0, 7.375]], "isOverall": false, "label": "/iam/profile [GET]]-Aggregated", "isController": false}, {"data": [[1.0, 5.6875]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.0, 5.6875]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 15.52941176470588]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.0, 15.52941176470588]], "isOverall": false, "label": "IAM / List Users-Aggregated", "isController": false}, {"data": [[1.0, 13.588235294117647]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.0, 13.588235294117647]], "isOverall": false, "label": "/iam/organization/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 4.015624999999999]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.0, 4.015624999999999]], "isOverall": false, "label": "/communications/sms [GET}-Aggregated", "isController": false}, {"data": [[1.0, 15.499999999999998]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.0, 15.499999999999998]], "isOverall": false, "label": "/iam/profile [POST]-Aggregated", "isController": false}, {"data": [[1.0, 8.9375]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.0, 8.9375]], "isOverall": false, "label": "ANF / DeleteANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 2.6406249999999996]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.0, 2.6406249999999996]], "isOverall": false, "label": "/communications/sms [GET} (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 4.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.0, 4.0]], "isOverall": false, "label": "Properties Details-Aggregated", "isController": false}, {"data": [[1.0, 7.562499999999999]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.0, 7.562499999999999]], "isOverall": false, "label": "Connected Test /  Add Device-Aggregated", "isController": false}, {"data": [[1.0, 7.187500000000001]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.0, 7.187500000000001]], "isOverall": false, "label": "/anf [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 3.703124999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.0, 3.703124999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 4.343749999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.0, 4.343749999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 5.718749999999999]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.0, 5.718749999999999]], "isOverall": false, "label": "/communications/event [POST]-Aggregated", "isController": false}, {"data": [[1.0, 5.0588235294117645]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.0, 5.0588235294117645]], "isOverall": false, "label": "/iam/organization/{id} [GET]-Aggregated", "isController": false}, {"data": [[1.0, 4.062499999999999]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.0, 4.062499999999999]], "isOverall": false, "label": "/communications/event [GET]-Aggregated", "isController": false}, {"data": [[1.0, 171.05882352941177]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.0, 171.05882352941177]], "isOverall": false, "label": "IAM / Change Password-Aggregated", "isController": false}, {"data": [[1.0, 4.1875]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.0, 4.1875]], "isOverall": false, "label": "/communications/event [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 4.5625]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.0, 4.5625]], "isOverall": false, "label": "/connected-test/manufacturer [GET]-Aggregated", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.0, 5.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor-Aggregated", "isController": false}, {"data": [[1.0, 17.176470588235293]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.0, 17.176470588235293]], "isOverall": false, "label": "/iam/user/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 8.882352941176473]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.0, 8.882352941176473]], "isOverall": false, "label": "IAM / Create Organization-Aggregated", "isController": false}, {"data": [[1.0, 4.593749999999999]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.0, 4.593749999999999]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "/connected-test/vendor [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.718750000000001]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.0, 5.718750000000001]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 7.499999999999999]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.0, 7.499999999999999]], "isOverall": false, "label": "Routine / CreateMedication-Aggregated", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "CommunicationService/listSMSTemplates-Aggregated", "isController": false}, {"data": [[1.0, 11.624999999999998]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.0, 11.624999999999998]], "isOverall": false, "label": "Connected Test /  SubmitTest-Aggregated", "isController": false}, {"data": [[1.0, 7.249999999999999]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.0, 7.249999999999999]], "isOverall": false, "label": "Routine / CreateDiagnosis-Aggregated", "isController": false}, {"data": [[1.0, 4.187500000000001]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.0, 4.187500000000001]], "isOverall": false, "label": "/connected-test/country [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.0, 5.125]], "isOverall": false, "label": "Connected Test /  Get Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.0, 5.937499999999999]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior-Aggregated", "isController": false}, {"data": [[1.0, 7.176470588235294]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.0, 7.176470588235294]], "isOverall": false, "label": "/connected-test/country [GET]-Aggregated", "isController": false}, {"data": [[1.0, 141.37500000000003]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.0, 141.37500000000003]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases-Aggregated", "isController": true}, {"data": [[1.0, 9.125]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.0, 9.125]], "isOverall": false, "label": "Classification / Classify-Aggregated", "isController": false}, {"data": [[1.0, 89.88235294117646]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.0, 89.88235294117646]], "isOverall": false, "label": "IAM / Login-Aggregated", "isController": false}, {"data": [[1.0, 6.1875]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.0, 6.1875]], "isOverall": false, "label": "/routine/labResult [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.0, 5.375]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution-Aggregated", "isController": false}, {"data": [[1.0, 8.125]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.0, 8.125]], "isOverall": false, "label": "Media / UpdateMedia-Aggregated", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "ANF / UpdateANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 3.5]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.0, 3.5]], "isOverall": false, "label": "/communications/email [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 5.500000000000001]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.0, 5.500000000000001]], "isOverall": false, "label": "/media [GET]-Aggregated", "isController": false}, {"data": [[1.0, 72.125]], "isOverall": false, "label": "Protector", "isController": true}, {"data": [[1.0, 72.125]], "isOverall": false, "label": "Protector-Aggregated", "isController": true}], "supportsControllersDiscrimination": true, "maxX": 1.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 1623.6666666666667, "minX": 1.70593926E12, "maxY": 185786.23333333334, "series": [{"data": [[1.70593926E12, 10497.45], [1.70593932E12, 185786.23333333334]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.70593926E12, 1623.6666666666667], [1.70593932E12, 27373.066666666666]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70593932E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 0.8124999999999999, "minX": 1.70593926E12, "maxY": 1561.0, "series": [{"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.7333333333333325]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 4.933333333333334]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 4.866666666666666]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.666666666666667]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 5.850000000000001]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.70593926E12, 4.25], [1.70593932E12, 5.166666666666667]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.783333333333332]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 5.333333333333334]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 8.133333333333335]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.70593926E12, 2.25], [1.70593932E12, 2.7499999999999987]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 5.75], [1.70593932E12, 6.7333333333333325]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70593932E12, 4.8125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.70593926E12, 21.25], [1.70593932E12, 5.866666666666668]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.70593932E12, 17.500000000000004]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 4.9333333333333345]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.70593926E12, 26.0], [1.70593932E12, 5.062500000000001]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.70593932E12, 12.437499999999998]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.70593932E12, 5.937499999999999]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 8.75]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.70593926E12, 25.0], [1.70593932E12, 4.800000000000001]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.70593926E12, 2.5], [1.70593932E12, 2.8833333333333337]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.999999999999999]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 7.866666666666668]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.70593932E12, 6.249999999999999]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70593932E12, 6.625]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.916666666666666]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.133333333333334]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.70593926E12, 21.0], [1.70593932E12, 4.75]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.116666666666668]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.70593926E12, 40.0], [1.70593932E12, 3.1999999999999997]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 8.812500000000002]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 10.799999999999999]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 6.333333333333334]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 5.0625]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.70593926E12, 1561.0], [1.70593932E12, 1084.6666666666665]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.8]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.70593932E12, 6.562500000000001]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70593932E12, 6.437500000000001]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 6.533333333333332]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 8.3125]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.70593926E12, 18.5], [1.70593932E12, 3.3000000000000003]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 18.25], [1.70593932E12, 2.4333333333333336]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.533333333333333]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.70593932E12, 7.187500000000001]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.70593932E12, 27.625]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.70593926E12, 14.0], [1.70593932E12, 9.5]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.70593932E12, 7.750000000000002]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.0625]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 5.5625]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70593932E12, 53.43750000000001]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.70593932E12, 6.5625]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 7.866666666666668]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.933333333333333]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.70593932E12, 5.75]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.70593926E12, 21.0], [1.70593932E12, 4.066666666666667]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 25.0], [1.70593932E12, 10.125]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.70593932E12, 5.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.70593932E12, 10.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.70593932E12, 15.000000000000002]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 6.333333333333334]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 5.333333333333334]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.70593926E12, 160.0], [1.70593932E12, 174.12499999999997]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.70593926E12, 4.5], [1.70593932E12, 5.066666666666666]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 3.933333333333333]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.4]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.70593926E12, 379.0], [1.70593932E12, 141.93333333333334]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.70593926E12, 3.0], [1.70593932E12, 0.8124999999999999]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 8.687500000000002]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.70593926E12, 20.5], [1.70593932E12, 5.583333333333334]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 3.2333333333333325]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.70593932E12, 8.875000000000002]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.70593932E12, 125.00000000000001]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 13.133333333333333]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.70593926E12, 18.0], [1.70593932E12, 8.666666666666668]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 8.133333333333335]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.70593926E12, 34.0], [1.70593932E12, 16.266666666666666]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 9.933333333333334]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.70593926E12, 502.0], [1.70593932E12, 180.13333333333335]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.70593926E12, 13.0], [1.70593932E12, 10.4]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 4.933333333333333]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 6.533333333333333]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.70593926E12, 391.0], [1.70593932E12, 155.26666666666662]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 6.0]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 10.066666666666668]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.533333333333334]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.70593926E12, 27.0], [1.70593932E12, 7.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.70593926E12, 165.0], [1.70593932E12, 178.8125]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.70593926E12, 84.0], [1.70593932E12, 48.99999999999999]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.883333333333334]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70593932E12, 4.874999999999999]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 6.8]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.70593926E12, 25.0], [1.70593932E12, 14.799999999999999]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.70593926E12, 30.0], [1.70593932E12, 10.28125]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.70593926E12, 27.0], [1.70593932E12, 13.333333333333334]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.70593926E12, 25.0], [1.70593932E12, 5.6875]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 10.249999999999998]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.70593932E12, 11.125]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.666666666666665]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.70593926E12, 24.0], [1.70593932E12, 9.875]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.866666666666666]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70593926E12, 26.0], [1.70593932E12, 6.875]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 4.749999999999999]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.70593932E12, 4.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.70593926E12, 128.0], [1.70593932E12, 105.25]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.70593926E12, 3.75], [1.70593932E12, 3.6333333333333333]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 6.066666666666667]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.70593926E12, 18.5], [1.70593932E12, 4.266666666666667]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.866666666666667]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.70593926E12, 15.0], [1.70593932E12, 9.533333333333333]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.70593932E12, 5.125]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.70593926E12, 20.25], [1.70593932E12, 4.216666666666666]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 15.0], [1.70593932E12, 12.733333333333333]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.70593926E12, 5.25], [1.70593932E12, 6.116666666666667]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.70593932E12, 8.375]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.066666666666666]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 7.133333333333333]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.70593926E12, 24.0], [1.70593932E12, 7.187499999999998]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.70593932E12, 5.062499999999999]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.70593926E12, 16.75], [1.70593932E12, 2.9166666666666674]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.70593926E12, 166.0], [1.70593932E12, 97.13333333333334]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.70593926E12, 363.0], [1.70593932E12, 137.66666666666669]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.066666666666666]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 20.26666666666667]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 6.933333333333333]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70593932E12, 5.5]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.70593926E12, 7.5], [1.70593932E12, 9.343749999999996]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.70593926E12, 17.0], [1.70593932E12, 4.2]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.70593926E12, 20.25], [1.70593932E12, 4.549999999999999]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 18.0], [1.70593932E12, 3.8666666666666663]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 23.0], [1.70593932E12, 5.3500000000000005]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.733333333333333]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.70593926E12, 124.0], [1.70593932E12, 81.59999999999998]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70593926E12, 20.75], [1.70593932E12, 5.566666666666666]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.70593932E12, 6.562500000000001]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.70593926E12, 21.0], [1.70593932E12, 5.766666666666668]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.70593926E12, 62.0], [1.70593932E12, 45.26666666666666]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.466666666666667]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.70593926E12, 209.0], [1.70593932E12, 93.0625]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 3.8666666666666667]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.70593926E12, 62.0], [1.70593932E12, 14.375]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.70593926E12, 4.5], [1.70593932E12, 4.883333333333334]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.733333333333333]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.70593926E12, 18.0], [1.70593932E12, 5.200000000000001]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 7.866666666666668]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.70593932E12, 12.625]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.466666666666667]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.70593932E12, 5.6875]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.70593926E12, 38.0], [1.70593932E12, 14.125000000000002]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.70593926E12, 46.0], [1.70593932E12, 11.562499999999998]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 4.0500000000000025]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.70593926E12, 11.0], [1.70593932E12, 15.799999999999999]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 8.2]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.70593926E12, 2.5], [1.70593932E12, 2.6499999999999995]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.70593926E12, 4.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 6.733333333333333]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.2666666666666675]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.70593926E12, 19.5], [1.70593932E12, 2.65]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 18.5], [1.70593932E12, 3.4000000000000012]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.766666666666667]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.0625]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 4.1000000000000005]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.70593926E12, 181.0], [1.70593932E12, 170.43750000000003]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 4.233333333333334]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.6000000000000005]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.70593932E12, 5.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 17.5]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.70593926E12, 24.0], [1.70593932E12, 7.937499999999999]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.70593926E12, 17.75], [1.70593932E12, 3.7166666666666672]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.466666666666667]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.70593926E12, 22.25], [1.70593932E12, 4.616666666666667]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.70593932E12, 7.499999999999999]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.70593926E12, 20.0], [1.70593932E12, 4.999999999999999]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70593926E12, 23.0], [1.70593932E12, 10.866666666666667]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.70593932E12, 7.249999999999999]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70593932E12, 4.187500000000001]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.70593926E12, 17.0], [1.70593932E12, 4.333333333333334]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.70593932E12, 5.937499999999999]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 7.375]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.70593926E12, 296.0], [1.70593932E12, 131.0666666666667]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.70593932E12, 9.125]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.70593926E12, 118.0], [1.70593932E12, 88.12499999999999]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.266666666666666]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.70593932E12, 5.375]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.70593926E12, 19.0], [1.70593932E12, 7.4]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.70593926E12, 21.0], [1.70593932E12, 10.733333333333336]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 3.5]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.6]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.70593932E12, 72.125]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70593932E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.70593926E12, "maxY": 621.0, "series": [{"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.666666666666667]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 4.933333333333334]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 4.8]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.4]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 5.75]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.066666666666666]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.683333333333334]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 8.133333333333335]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.70593926E12, 2.25], [1.70593932E12, 2.6833333333333336]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 5.75], [1.70593932E12, 6.550000000000001]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70593932E12, 4.8125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.70593932E12, 8.250000000000002]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 8.5]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.70593926E12, 2.5], [1.70593932E12, 2.8000000000000003]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.933333333333333]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 7.666666666666666]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.766666666666667]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.200000000000001]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.033333333333333]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 8.687500000000002]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 10.600000000000001]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.70593926E12, 621.0], [1.70593932E12, 600.1333333333332]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.6000000000000005]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70593932E12, 6.437500000000001]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 8.3125]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.4]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.70593932E12, 12.312500000000002]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.70593926E12, 14.0], [1.70593932E12, 9.375]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.687499999999999]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 5.500000000000001]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70593932E12, 25.125000000000004]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.8]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.70593932E12, 5.5]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.70593932E12, 4.875]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.2]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.70593926E12, 160.0], [1.70593932E12, 174.12499999999997]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.033333333333333]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.200000000000001]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.70593926E12, 77.0], [1.70593932E12, 75.06666666666666]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.70593926E12, 9.0], [1.70593932E12, 8.562500000000002]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 3.0999999999999996]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.70593932E12, 64.875]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 8.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 8.133333333333335]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.70593926E12, 81.0], [1.70593932E12, 94.93333333333334]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.70593926E12, 13.0], [1.70593932E12, 10.3]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.70593926E12, 63.0], [1.70593932E12, 82.6]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.933333333333334]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.70593926E12, 8.0], [1.70593932E12, 9.666666666666668]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.466666666666668]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.70593926E12, 164.0], [1.70593932E12, 178.75]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.70593926E12, 22.0], [1.70593932E12, 24.600000000000005]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.800000000000002]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 10.0]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.70593932E12, 10.875]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.599999999999999]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.666666666666667]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.70593932E12, 4.250000000000001]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.70593926E12, 127.0], [1.70593932E12, 105.1875]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.70593926E12, 3.75], [1.70593932E12, 3.6000000000000005]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.733333333333334]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.70593926E12, 14.0], [1.70593932E12, 9.066666666666666]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.70593932E12, 4.749999999999999]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 15.0], [1.70593932E12, 12.466666666666667]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.9]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.70593932E12, 8.250000000000002]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 6.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70593926E12, 10.0], [1.70593932E12, 7.133333333333333]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.70593926E12, 64.0], [1.70593932E12, 61.6]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.70593926E12, 64.0], [1.70593932E12, 67.53333333333333]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 6.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.70593926E12, 18.0], [1.70593932E12, 20.0]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70593932E12, 5.5]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.70593926E12, 7.5], [1.70593932E12, 9.343749999999996]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.4]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.70593926E12, 36.0], [1.70593932E12, 36.666666666666664]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.70593926E12, 17.0], [1.70593932E12, 22.866666666666667]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 6.2666666666666675]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.70593926E12, 4.25], [1.70593932E12, 4.766666666666669]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.6000000000000005]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.70593926E12, 7.0], [1.70593932E12, 7.533333333333333]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.70593932E12, 12.312500000000002]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.333333333333334]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.70593932E12, 5.5]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.70593926E12, 45.0], [1.70593932E12, 8.5625]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 3.9833333333333343]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.70593926E12, 11.0], [1.70593932E12, 15.533333333333331]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.70593926E12, 2.5], [1.70593932E12, 2.5833333333333335]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70593926E12, 6.0], [1.70593932E12, 7.000000000000002]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.6333333333333355]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 4.9375]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 4.066666666666668]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 4.233333333333334]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 4.6000000000000005]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 17.5]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.4]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70593932E12, 4.125000000000001]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 7.3125]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.70593926E12, 58.0], [1.70593932E12, 67.53333333333333]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.70593926E12, 5.0], [1.70593932E12, 5.933333333333333]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.70593926E12, 3.5], [1.70593932E12, 3.4]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.70593926E12, 4.0], [1.70593932E12, 5.6]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.70593932E12, 31.499999999999996]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70593932E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.70593926E12, "maxY": 32.0, "series": [{"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.70593926E12, 32.0], [1.70593932E12, 13.333333333333334]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.70593926E12, 32.0], [1.70593932E12, 13.4375]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.70593926E12, 0.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.70593932E12, 0.0]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70593932E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.70593926E12, "maxY": 277.0, "series": [{"data": [[1.70593926E12, 209.0], [1.70593932E12, 277.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.70593926E12, 24.0], [1.70593932E12, 11.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.70593926E12, 173.96000000000004], [1.70593932E12, 164.0]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.70593926E12, 32.39999999999995], [1.70593932E12, 16.0]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.70593926E12, 2.0], [1.70593932E12, 0.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.70593926E12, 12.0], [1.70593932E12, 5.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70593932E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 3.5, "minX": 2.0, "maxY": 68.0, "series": [{"data": [[2.0, 3.5], [4.0, 68.0], [5.0, 26.0], [6.0, 20.5], [7.0, 18.0], [8.0, 21.0], [12.0, 20.5], [13.0, 14.0], [18.0, 7.0], [19.0, 6.0], [20.0, 9.5], [22.0, 4.0], [23.0, 5.0], [25.0, 5.0], [26.0, 5.0], [32.0, 8.5], [35.0, 8.0], [34.0, 9.5], [36.0, 9.0], [40.0, 6.0], [43.0, 7.0], [44.0, 6.0], [49.0, 7.0], [52.0, 8.5], [61.0, 7.0], [65.0, 9.0], [68.0, 7.0], [69.0, 5.0], [76.0, 5.0], [86.0, 7.0], [90.0, 5.0], [92.0, 8.0], [103.0, 5.0], [111.0, 5.0], [119.0, 5.0], [121.0, 4.0], [126.0, 5.0], [134.0, 4.0], [131.0, 5.0], [143.0, 6.0], [144.0, 5.0], [146.0, 4.0], [162.0, 5.0], [172.0, 5.0], [176.0, 4.0], [179.0, 4.0], [181.0, 4.0], [186.0, 5.0], [199.0, 4.0], [200.0, 4.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 200.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 0.0, "minX": 2.0, "maxY": 7.5, "series": [{"data": [[2.0, 0.0], [4.0, 7.5], [5.0, 0.0], [6.0, 0.0], [7.0, 0.0], [8.0, 0.0], [12.0, 0.0], [13.0, 6.0], [18.0, 4.0], [19.0, 4.0], [20.0, 6.0], [22.0, 4.0], [23.0, 3.0], [25.0, 3.0], [26.0, 4.0], [32.0, 2.5], [35.0, 0.0], [34.0, 2.0], [36.0, 2.0], [40.0, 0.0], [43.0, 0.0], [44.0, 0.0], [49.0, 0.0], [52.0, 0.0], [61.0, 0.0], [65.0, 1.5], [68.0, 0.0], [69.0, 0.0], [76.0, 0.0], [86.0, 4.0], [90.0, 0.0], [92.0, 4.0], [103.0, 2.0], [111.0, 0.0], [119.0, 3.0], [121.0, 0.0], [126.0, 3.0], [134.0, 2.0], [131.0, 3.0], [143.0, 4.0], [144.0, 3.0], [146.0, 0.0], [162.0, 2.0], [172.0, 2.0], [176.0, 3.0], [179.0, 2.0], [181.0, 2.0], [186.0, 2.0], [199.0, 2.5], [200.0, 2.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 200.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
}

var hitsPerSecondInfos = {
        data: {"result": {"minY": 4.05, "minX": 1.70593926E12, "maxY": 69.63333333333334, "series": [{"data": [[1.70593926E12, 4.05], [1.70593932E12, 69.63333333333334]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70593932E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 4.05, "minX": 1.70593926E12, "maxY": 69.63333333333334, "series": [{"data": [[1.70593926E12, 4.05], [1.70593932E12, 69.63333333333334]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70593932E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 0.016666666666666666, "minX": 1.70593926E12, "maxY": 1.0, "series": [{"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/anf [DELETE]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/iam/profile [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateDiagnosis-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Create Organization-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5]], "isOverall": false, "label": "/media/download-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/organization [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/anf [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  ListConnectedTests-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Get Country-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "ANF / CreateANFStatement-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/labOrder [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/country [POST]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "IAM Profile-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5333333333333333]], "isOverall": false, "label": "IAM / Update Workspace-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/sms [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/vendor [DELETE]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "IAM / Get User Profile-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "AuditService / Event-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/labResult [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Get User-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/anf [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Update User-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector / DetectAnomalies-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "ANF / DeleteANFStatement-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/media [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Update Vendor-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/testcase [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/list [POST]]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/email/list [POST]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Update Country-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/event [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5]], "isOverall": false, "label": "/communications/event [DELETE]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "ANF / UpdateANFStatement-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/testcase [DELETE]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Delete Device-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/event/audit-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/email [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5]], "isOverall": false, "label": "/communications/sms [DELETE}-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/email [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5]], "isOverall": false, "label": "/media/upload-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/testcase [GET]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateRoutine-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Communications Low-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media / GetMedia-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateLabResult-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/manufacturer [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/email [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/protector/authorize [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Change Password-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector / ProtectPrivacy-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Communications Immediate-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Get TestCase-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media / UpdateMedia-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Predictor-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/medication [POST]]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/organization [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/event/list [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Get Device-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/classify [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Add Manufacturer-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Get Manufacturer-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/email [GET] (Cache)-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media / ListMedia-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Delete Country-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5333333333333333]], "isOverall": false, "label": "/iam/user/verify [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/diagnosis [POST]]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Delete TestCase-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Create Workspace-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/event [GET] (Cache)-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / List Users-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/sms [POST]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Classification / Classify-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/device [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/media/list  [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user/password [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/device [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/sms [GET}-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Communications Medium-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/manufacturer [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/country [PUT]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/iam/workspace [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/vendor [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "ANF-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/iam/workspace/{id} [GET]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Delete Vendor-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Add Device-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine-success", "isController": true}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/anf [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/vendor [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/iam/workspace/list [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/country [DELETE]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/country [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / List Organization-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateLabOrder-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user/current [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/event [PUT]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Current User-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/routine [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user/list [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/media [GET]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/vendor [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/device [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/media [PUT]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/organization/{id} [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/event [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user/login {POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "ANF / GetANFStatement-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Audit-success", "isController": true}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate-success", "isController": false}, {"data": [[1.70593926E12, 0.03333333333333333], [1.70593932E12, 0.5]], "isOverall": false, "label": "/communications/email [DELETE]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Classification-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666]], "isOverall": false, "label": "Properties Details-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/organization/list [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media / DeleteMedia-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Login-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/SendNotification-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "IAM-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  SubmitTest-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/sms [GET} (Cache)-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/notification [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test - Connected Tests-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Communications High-success", "isController": true}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateMedication-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test/testcase [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Signup-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user [GET]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Thread Setup-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/connected-test [GET]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user [PUT]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/workspace [POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "IAM / Update User Profile-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test - Delete-success", "isController": true}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Add Vendor-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/iam/user/signup {POST]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "/communications/sms/list [POST]-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Routine / CreateDeliveryTracking-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Get Vendor-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Add TestCase-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Media / CreateMedia-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Update Device-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/connected-test/device [DELETE]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / Update Organization-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Update Manufacturer-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Predictor / Predict-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/media  [DELETE]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "Connected Test /  Update TestCase-success", "isController": false}, {"data": [[1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "/predictor [POST]]-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.26666666666666666]], "isOverall": false, "label": "Connected Test /  Add Country-success", "isController": false}, {"data": [[1.70593926E12, 0.016666666666666666], [1.70593932E12, 0.25]], "isOverall": false, "label": "/iam/profile [GET]]-success", "isController": false}, {"data": [[1.70593926E12, 0.06666666666666667], [1.70593932E12, 1.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70593932E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var totalTPSInfos = {
        data: {"result": {"minY": 4.233333333333333, "minX": 1.70593926E12, "maxY": 73.71666666666667, "series": [{"data": [[1.70593926E12, 4.233333333333333], [1.70593932E12, 73.71666666666667]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70593932E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, -18000000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}
