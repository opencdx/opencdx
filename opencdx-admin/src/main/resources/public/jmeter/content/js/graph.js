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
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 32.0, "series": [{"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/ruleset [POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1300.0, 3.0], [1400.0, 4.0], [1900.0, 1.0]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[0.0, 1.0], [200.0, 1.0], [100.0, 5.0]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[200.0, 2.0], [100.0, 6.0]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[300.0, 3.0], [200.0, 3.0], [400.0, 1.0], [500.0, 1.0]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/questionnaire [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[300.0, 2.0], [700.0, 1.0], [400.0, 3.0], [200.0, 2.0]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/ruleset [GET]]", "isController": false}, {"data": [[100.0, 8.0]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[200.0, 4.0], [100.0, 4.0]], "isOverall": false, "label": "Questionnaire", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[0.0, 7.0], [100.0, 1.0]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[0.0, 5.0], [200.0, 1.0], [100.0, 2.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[0.0, 1.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Questionnaire / CreateRuleSet", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[300.0, 3.0], [400.0, 1.0], [200.0, 4.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[0.0, 4.0], [100.0, 4.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[200.0, 1.0], [100.0, 7.0]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Questionnaire / GetRuleSets", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/ruleset/list[GET]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[300.0, 5.0], [200.0, 3.0]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/user/questionnaire [POST]", "isController": false}, {"data": [[300.0, 5.0], [200.0, 2.0], [500.0, 1.0]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[100.0, 5.0], [200.0, 3.0]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[100.0, 8.0]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[200.0, 3.0], [100.0, 5.0]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[600.0, 1.0], [300.0, 2.0], [200.0, 5.0]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Questionnaire / GetRuleSet", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[100.0, 5.0], [200.0, 3.0]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[0.0, 2.0], [100.0, 6.0]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/ruleset [PUT]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/questionnaire [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Media - Delete", "isController": true}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/questionnaire [POST]", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[100.0, 7.0], [200.0, 1.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[0.0, 16.0]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[0.0, 7.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[0.0, 32.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[0.0, 6.0], [100.0, 2.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[0.0, 8.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[200.0, 4.0], [100.0, 4.0]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1900.0, "title": "Response Time Distribution"}},
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
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 2282.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 2282.0, "series": [{"data": [[0.0, 2282.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 4.9E-324, "title": "Synthetic Response Times Distribution"}},
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
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 1.0, "minX": 1.7080338E12, "maxY": 1.0, "series": [{"data": [[1.7080338E12, 1.0]], "isOverall": false, "label": "Setup Details", "isController": false}, {"data": [[1.7080338E12, 1.0], [1.70803386E12, 1.0]], "isOverall": false, "label": "Integration Test", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70803386E12, "title": "Active Threads Over Time"}},
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
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 1.25, "minX": 1.0, "maxY": 1482.25, "series": [{"data": [[1.0, 17.625]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.0, 17.625]], "isOverall": false, "label": "/connected-test/device [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 16.875]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.0, 16.875]], "isOverall": false, "label": "Connected Test /  Update Device-Aggregated", "isController": false}, {"data": [[1.0, 13.21875]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.0, 13.21875]], "isOverall": false, "label": "/communications/event/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 10.125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.0, 10.125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 25.875]], "isOverall": false, "label": "/ruleset [POST]", "isController": false}, {"data": [[1.0, 25.875]], "isOverall": false, "label": "/ruleset [POST]-Aggregated", "isController": false}, {"data": [[1.0, 13.1875]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.0, 13.1875]], "isOverall": false, "label": "CommunicationService/SendNotification-Aggregated", "isController": false}, {"data": [[1.0, 14.875]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.0, 14.875]], "isOverall": false, "label": "Connected Test /  Update Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 15.875]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet", "isController": false}, {"data": [[1.0, 15.875]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet-Aggregated", "isController": false}, {"data": [[1.0, 32.25]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.0, 32.25]], "isOverall": false, "label": "Protector / DetectAnomalies-Aggregated", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity-Aggregated", "isController": false}, {"data": [[1.0, 14.25]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.0, 14.25]], "isOverall": false, "label": "Connected Test /  Update TestCase-Aggregated", "isController": false}, {"data": [[1.0, 15.75]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.0, 15.75]], "isOverall": false, "label": "/media [PUT]]-Aggregated", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "Protector / ProtectPrivacy-Aggregated", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "Connected Test /  Delete Device-Aggregated", "isController": false}, {"data": [[1.0, 13.78125]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.0, 13.78125]], "isOverall": false, "label": "/communications/sms [POST]-Aggregated", "isController": false}, {"data": [[1.0, 33.25]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.0, 33.25]], "isOverall": false, "label": "/connected-test [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 1482.25]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.0, 1482.25]], "isOverall": false, "label": "IAM-Aggregated", "isController": true}, {"data": [[1.0, 19.5]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.0, 19.5]], "isOverall": false, "label": "/routine/medication [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 18.625]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.0, 18.625]], "isOverall": false, "label": "Routine / CreateLabResult-Aggregated", "isController": false}, {"data": [[1.0, 17.625]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.0, 17.625]], "isOverall": false, "label": "/connected-test/testcase [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 18.124999999999996]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.0, 18.124999999999996]], "isOverall": false, "label": "/iam/organization [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 9.09375]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.0, 9.09375]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/connected-test/country [POST]-Aggregated", "isController": false}, {"data": [[1.0, 11.5]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.0, 11.5]], "isOverall": false, "label": "/connected-test/country [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 127.85714285714286]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.0, 127.85714285714286]], "isOverall": false, "label": "Connected Test - Delete-Aggregated", "isController": true}, {"data": [[1.0, 15.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.0, 15.0]], "isOverall": false, "label": "Media / CreateMedia-Aggregated", "isController": false}, {"data": [[1.0, 21.625]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.0, 21.625]], "isOverall": false, "label": "/routine/diagnosis [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 26.625]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.0, 26.625]], "isOverall": false, "label": "Predictor / Predict-Aggregated", "isController": false}, {"data": [[1.0, 190.875]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.0, 190.875]], "isOverall": false, "label": "/iam/user/login {POST]-Aggregated", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "Connected Test /  Get Device-Aggregated", "isController": false}, {"data": [[1.0, 363.87499999999994]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.0, 363.87499999999994]], "isOverall": false, "label": "Communications Low-Aggregated", "isController": true}, {"data": [[1.0, 1.25]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.0, 1.25]], "isOverall": false, "label": "Thread Setup-Aggregated", "isController": false}, {"data": [[1.0, 15.03125]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.0, 15.03125]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 8.125000000000004]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.0, 8.125000000000004]], "isOverall": false, "label": "/communications/sms [DELETE}-Aggregated", "isController": false}, {"data": [[1.0, 22.375]], "isOverall": false, "label": "/questionnaire [PUT]", "isController": false}, {"data": [[1.0, 22.375]], "isOverall": false, "label": "/questionnaire [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 14.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.0, 14.0]], "isOverall": false, "label": "/iam/workspace/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "IAM / Get User Profile-Aggregated", "isController": false}, {"data": [[1.0, 34.25]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.0, 34.25]], "isOverall": false, "label": "Audit-Aggregated", "isController": true}, {"data": [[1.0, 21.625]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.0, 21.625]], "isOverall": false, "label": "ANF / GetANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 430.875]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.0, 430.875]], "isOverall": false, "label": "Communications Immediate-Aggregated", "isController": true}, {"data": [[1.0, 19.437499999999996]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.0, 19.437499999999996]], "isOverall": false, "label": "/media/upload-Aggregated", "isController": false}, {"data": [[1.0, 23.5]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.0, 23.5]], "isOverall": false, "label": "/anf [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.0, 18.125]], "isOverall": false, "label": "IAM / Create Workspace-Aggregated", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "/ruleset [GET]]", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "/ruleset [GET]]-Aggregated", "isController": false}, {"data": [[1.0, 139.0]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.0, 139.0]], "isOverall": false, "label": "Connected Test - Connected Tests-Aggregated", "isController": true}, {"data": [[1.0, 12.71875]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.0, 12.71875]], "isOverall": false, "label": "/communications/sms/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 10.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.0, 10.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Country-Aggregated", "isController": false}, {"data": [[1.0, 216.0]], "isOverall": false, "label": "Questionnaire", "isController": true}, {"data": [[1.0, 216.0]], "isOverall": false, "label": "Questionnaire-Aggregated", "isController": true}, {"data": [[1.0, 18.875]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.0, 18.875]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 34.5]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.0, 34.5]], "isOverall": false, "label": "ANF / CreateANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 15.249999999999998]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.0, 15.249999999999998]], "isOverall": false, "label": "IAM / Update Workspace-Aggregated", "isController": false}, {"data": [[1.0, 41.99999999999999]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.0, 41.99999999999999]], "isOverall": false, "label": "IAM / Update User Profile-Aggregated", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "IAM / Get User-Aggregated", "isController": false}, {"data": [[1.0, 20.75]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.0, 20.75]], "isOverall": false, "label": "/iam/user [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 19.249999999999996]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.0, 19.249999999999996]], "isOverall": false, "label": "IAM / Update User-Aggregated", "isController": false}, {"data": [[1.0, 15.999999999999998]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.0, 15.999999999999998]], "isOverall": false, "label": "IAM / Update Organization-Aggregated", "isController": false}, {"data": [[1.0, 11.500000000000002]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.0, 11.500000000000002]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById-Aggregated", "isController": false}, {"data": [[1.0, 9.875000000000002]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.0, 9.875000000000002]], "isOverall": false, "label": "Media / GetMedia-Aggregated", "isController": false}, {"data": [[1.0, 24.25]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.0, 24.25]], "isOverall": false, "label": "/routine [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 12.875000000000002]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.0, 12.875000000000002]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 10.562499999999998]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.0, 10.562499999999998]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 99.125]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.0, 99.125]], "isOverall": false, "label": "/classify [POST]-Aggregated", "isController": false}, {"data": [[1.0, 15.375]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.0, 15.375]], "isOverall": false, "label": "/iam/workspace [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 13.124999999999998]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.0, 13.124999999999998]], "isOverall": false, "label": "Connected Test /  Update Country-Aggregated", "isController": false}, {"data": [[1.0, 7.875000000000002]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.0, 7.875000000000002]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 15.75]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.0, 15.75]], "isOverall": false, "label": "/media [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 55.75]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.0, 55.75]], "isOverall": false, "label": "/routine/labOrder [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "Connected Test /  Get Vendor-Aggregated", "isController": false}, {"data": [[1.0, 11.25]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.0, 11.25]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 8.625]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.0, 8.625]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 13.499999999999998]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.0, 13.499999999999998]], "isOverall": false, "label": "/connected-test/device [POST]-Aggregated", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "CommunicationService/listNotificationEvents-Aggregated", "isController": false}, {"data": [[1.0, 11.312499999999998]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.0, 11.312499999999998]], "isOverall": false, "label": "CommunicationService/listEmailTemplates-Aggregated", "isController": false}, {"data": [[1.0, 116.125]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.0, 116.125]], "isOverall": false, "label": "IAM / Signup-Aggregated", "isController": false}, {"data": [[1.0, 12.937499999999998]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.0, 12.937499999999998]], "isOverall": false, "label": "/communications/event [PUT]]-Aggregated", "isController": false}, {"data": [[1.0, 8.624999999999998]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.0, 8.624999999999998]], "isOverall": false, "label": "/connected-test/testcase [GET]-Aggregated", "isController": false}, {"data": [[1.0, 31.625]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.0, 31.625]], "isOverall": false, "label": "/predictor [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 15.875000000000002]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.0, 15.875000000000002]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/communications/sms [GET}-Aggregated", "isController": false}, {"data": [[1.0, 35.99999999999999]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.0, 35.99999999999999]], "isOverall": false, "label": "/iam/profile [POST]-Aggregated", "isController": false}, {"data": [[1.0, 4.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.0, 4.0]], "isOverall": false, "label": "Properties Details-Aggregated", "isController": false}, {"data": [[1.0, 26.000000000000004]], "isOverall": false, "label": "Questionnaire / CreateRuleSet", "isController": false}, {"data": [[1.0, 26.000000000000004]], "isOverall": false, "label": "Questionnaire / CreateRuleSet-Aggregated", "isController": false}, {"data": [[1.0, 8.4375]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.0, 8.4375]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 12.75]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.0, 12.75]], "isOverall": false, "label": "/iam/organization/{id} [GET]-Aggregated", "isController": false}, {"data": [[1.0, 8.1875]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.0, 8.1875]], "isOverall": false, "label": "/communications/event [GET]-Aggregated", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.0, 9.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]-Aggregated", "isController": false}, {"data": [[1.0, 50.625]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.0, 50.625]], "isOverall": false, "label": "/iam/user/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 17.250000000000004]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.0, 17.250000000000004]], "isOverall": false, "label": "IAM / Create Organization-Aggregated", "isController": false}, {"data": [[1.0, 13.312500000000002]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.0, 13.312500000000002]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 31.375]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.0, 31.375]], "isOverall": false, "label": "Connected Test /  SubmitTest-Aggregated", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "Routine / CreateDiagnosis-Aggregated", "isController": false}, {"data": [[1.0, 9.499999999999998]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.0, 9.499999999999998]], "isOverall": false, "label": "/connected-test/country [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "Connected Test /  Get Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 15.625]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.0, 15.625]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior-Aggregated", "isController": false}, {"data": [[1.0, 10.125000000000002]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.0, 10.125000000000002]], "isOverall": false, "label": "/connected-test/country [GET]-Aggregated", "isController": false}, {"data": [[1.0, 320.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.0, 320.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases-Aggregated", "isController": true}, {"data": [[1.0, 103.87499999999999]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.0, 103.87499999999999]], "isOverall": false, "label": "IAM / Login-Aggregated", "isController": false}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/routine/labResult [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 14.125]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.0, 14.125]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution-Aggregated", "isController": false}, {"data": [[1.0, 24.5]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.0, 24.5]], "isOverall": false, "label": "ANF / UpdateANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 9.25]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.0, 9.25]], "isOverall": false, "label": "/communications/email [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "/media [GET]-Aggregated", "isController": false}, {"data": [[1.0, 8.875]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.0, 8.875]], "isOverall": false, "label": "/connected-test/vendor [GET]-Aggregated", "isController": false}, {"data": [[1.0, 13.25]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.0, 13.25]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 9.875]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.0, 9.875]], "isOverall": false, "label": "/iam/workspace/{id} [GET]-Aggregated", "isController": false}, {"data": [[1.0, 12.124999999999998]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.0, 12.124999999999998]], "isOverall": false, "label": "/communications/email [POST]-Aggregated", "isController": false}, {"data": [[1.0, 11.718750000000002]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.0, 11.718750000000002]], "isOverall": false, "label": "/communications/email [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 11.437500000000004]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.0, 11.437500000000004]], "isOverall": false, "label": "/communications/sms [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 19.0]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.0, 19.0]], "isOverall": false, "label": "/event/audit-Aggregated", "isController": false}, {"data": [[1.0, 6.406249999999998]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.0, 6.406249999999998]], "isOverall": false, "label": "/communications/email [GET] (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 177.625]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.0, 177.625]], "isOverall": false, "label": "Classification-Aggregated", "isController": true}, {"data": [[1.0, 13.999999999999998]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.0, 13.999999999999998]], "isOverall": false, "label": "IAM / Current User-Aggregated", "isController": false}, {"data": [[1.0, 14.875]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.0, 14.875]], "isOverall": false, "label": "/iam/user [GET]]-Aggregated", "isController": false}, {"data": [[1.0, 6.312500000000002]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.0, 6.312500000000002]], "isOverall": false, "label": "/communications/event [GET] (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 15.999999999999998]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.0, 15.999999999999998]], "isOverall": false, "label": "/connected-test/testcase [POST]-Aggregated", "isController": false}, {"data": [[1.0, 14.625]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.0, 14.625]], "isOverall": false, "label": "/connected-test/manufacturer [POST]-Aggregated", "isController": false}, {"data": [[1.0, 13.625]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.0, 13.625]], "isOverall": false, "label": "Connected Test /  Get Country-Aggregated", "isController": false}, {"data": [[1.0, 13.875]], "isOverall": false, "label": "Questionnaire / GetRuleSets", "isController": false}, {"data": [[1.0, 13.875]], "isOverall": false, "label": "Questionnaire / GetRuleSets-Aggregated", "isController": false}, {"data": [[1.0, 11.843750000000002]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.0, 11.843750000000002]], "isOverall": false, "label": "/communications/email/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 8.500000000000002]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.0, 8.500000000000002]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 19.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.0, 19.0]], "isOverall": false, "label": "/iam/workspace [POST]-Aggregated", "isController": false}, {"data": [[1.0, 16.875]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.0, 16.875]], "isOverall": false, "label": "Connected Test /  ListConnectedTests-Aggregated", "isController": false}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Connected Test /  Add TestCase-Aggregated", "isController": false}, {"data": [[1.0, 7.437500000000001]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.0, 7.437500000000001]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 19.375]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.0, 19.375]], "isOverall": false, "label": "/connected-test [GET]-Aggregated", "isController": false}, {"data": [[1.0, 13.125]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.0, 13.125]], "isOverall": false, "label": "Routine / CreateDeliveryTracking-Aggregated", "isController": false}, {"data": [[1.0, 58.25]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.0, 58.25]], "isOverall": false, "label": "Predictor-Aggregated", "isController": true}, {"data": [[1.0, 19.25]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.0, 19.25]], "isOverall": false, "label": "Routine / CreateLabOrder-Aggregated", "isController": false}, {"data": [[1.0, 10.625]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.0, 10.625]], "isOverall": false, "label": "/iam/user/current [GET]-Aggregated", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis-Aggregated", "isController": false}, {"data": [[1.0, 15.0]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.0, 15.0]], "isOverall": false, "label": "/protector/authorize [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 21.875]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.0, 21.875]], "isOverall": false, "label": "Connected Test /  Add Country-Aggregated", "isController": false}, {"data": [[1.0, 13.125]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.0, 13.125]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 17.5]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.0, 17.5]], "isOverall": false, "label": "Routine / CreateRoutine-Aggregated", "isController": false}, {"data": [[1.0, 17.874999999999996]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.0, 17.874999999999996]], "isOverall": false, "label": "Connected Test /  Add Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 13.000000000000002]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.0, 13.000000000000002]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 15.249999999999998]], "isOverall": false, "label": "/ruleset/list[GET]", "isController": false}, {"data": [[1.0, 15.249999999999998]], "isOverall": false, "label": "/ruleset/list[GET]-Aggregated", "isController": false}, {"data": [[1.0, 10.4375]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.0, 10.4375]], "isOverall": false, "label": "/media/download-Aggregated", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "/connected-test/device [GET]-Aggregated", "isController": false}, {"data": [[1.0, 21.5]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.0, 21.5]], "isOverall": false, "label": "/iam/organization [POST]-Aggregated", "isController": false}, {"data": [[1.0, 20.375]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.0, 20.375]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl-Aggregated", "isController": false}, {"data": [[1.0, 317.25]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.0, 317.25]], "isOverall": false, "label": "Routine-Aggregated", "isController": true}, {"data": [[1.0, 15.250000000000002]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.0, 15.250000000000002]], "isOverall": false, "label": "AuditService / Event-Aggregated", "isController": false}, {"data": [[1.0, 14.375]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.0, 14.375]], "isOverall": false, "label": "Connected Test /  Add Vendor-Aggregated", "isController": false}, {"data": [[1.0, 16.125]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.0, 16.125]], "isOverall": false, "label": "Media / ListMedia-Aggregated", "isController": false}, {"data": [[1.0, 22.25]], "isOverall": false, "label": "/user/questionnaire [POST]", "isController": false}, {"data": [[1.0, 22.25]], "isOverall": false, "label": "/user/questionnaire [POST]-Aggregated", "isController": false}, {"data": [[1.0, 343.625]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.0, 343.625]], "isOverall": false, "label": "Communications High-Aggregated", "isController": true}, {"data": [[1.0, 11.75]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.0, 11.75]], "isOverall": false, "label": "/connected-test/testcase [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 195.875]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.0, 195.875]], "isOverall": false, "label": "/iam/user/password [POST]-Aggregated", "isController": false}, {"data": [[1.0, 24.75]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.0, 24.75]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 19.25]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.0, 19.25]], "isOverall": false, "label": "/connected-test/list [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 16.375]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.0, 16.375]], "isOverall": false, "label": "/media/list  [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 10.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.0, 10.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 125.99999999999999]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.0, 125.99999999999999]], "isOverall": false, "label": "/iam/user/signup {POST]-Aggregated", "isController": false}, {"data": [[1.0, 9.000000000000002]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.0, 9.000000000000002]], "isOverall": false, "label": "/communications/email [GET]-Aggregated", "isController": false}, {"data": [[1.0, 11.15625]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.0, 11.15625]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate-Aggregated", "isController": false}, {"data": [[1.0, 12.428571428571427]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.0, 12.428571428571427]], "isOverall": false, "label": "/media  [DELETE]]-Aggregated", "isController": false}, {"data": [[1.0, 25.875]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.0, 25.875]], "isOverall": false, "label": "/anf [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 13.75]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.0, 13.75]], "isOverall": false, "label": "/communications/notification [POST]-Aggregated", "isController": false}, {"data": [[1.0, 10.75]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.0, 10.75]], "isOverall": false, "label": "/connected-test/vendor [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 9.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.0, 9.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer-Aggregated", "isController": false}, {"data": [[1.0, 176.12499999999997]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.0, 176.12499999999997]], "isOverall": false, "label": "Media-Aggregated", "isController": true}, {"data": [[1.0, 328.875]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.0, 328.875]], "isOverall": false, "label": "Communications Medium-Aggregated", "isController": true}, {"data": [[1.0, 14.285714285714285]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.0, 14.285714285714285]], "isOverall": false, "label": "Media / DeleteMedia-Aggregated", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.0, 15.125]], "isOverall": false, "label": "/connected-test/device [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 22.0]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.0, 22.0]], "isOverall": false, "label": "/iam/user/verify [GET]-Aggregated", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "Questionnaire / GetRuleSet", "isController": false}, {"data": [[1.0, 14.5]], "isOverall": false, "label": "Questionnaire / GetRuleSet-Aggregated", "isController": false}, {"data": [[1.0, 13.593750000000002]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.0, 13.593750000000002]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 185.625]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.0, 185.625]], "isOverall": false, "label": "ANF-Aggregated", "isController": true}, {"data": [[1.0, 17.142857142857142]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.0, 17.142857142857142]], "isOverall": false, "label": "Connected Test /  Delete TestCase-Aggregated", "isController": false}, {"data": [[1.0, 111.75]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.0, 111.75]], "isOverall": false, "label": "IAM Profile-Aggregated", "isController": true}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.0, 20.125]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "/ruleset [PUT]", "isController": false}, {"data": [[1.0, 18.0]], "isOverall": false, "label": "/ruleset [PUT]-Aggregated", "isController": false}, {"data": [[1.0, 10.5]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.0, 10.5]], "isOverall": false, "label": "Connected Test /  Get TestCase-Aggregated", "isController": false}, {"data": [[1.0, 15.500000000000002]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.0, 15.500000000000002]], "isOverall": false, "label": "IAM / List Organization-Aggregated", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "/questionnaire [GET]", "isController": false}, {"data": [[1.0, 11.875]], "isOverall": false, "label": "/questionnaire [GET]-Aggregated", "isController": false}, {"data": [[1.0, 13.5]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.0, 13.5]], "isOverall": false, "label": "Connected Test /  Update Vendor-Aggregated", "isController": false}, {"data": [[1.0, 17.875]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.0, 17.875]], "isOverall": false, "label": "/anf [GET]-Aggregated", "isController": false}, {"data": [[1.0, 15.625]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.0, 15.625]], "isOverall": false, "label": "/iam/profile [GET]]-Aggregated", "isController": false}, {"data": [[1.0, 39.25]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.0, 39.25]], "isOverall": false, "label": "IAM / List Users-Aggregated", "isController": false}, {"data": [[1.0, 13.375]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.0, 13.375]], "isOverall": false, "label": "/iam/organization/list [POST]-Aggregated", "isController": false}, {"data": [[1.0, 17.25]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.0, 17.25]], "isOverall": false, "label": "ANF / DeleteANFStatement-Aggregated", "isController": false}, {"data": [[1.0, 6.374999999999998]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.0, 6.374999999999998]], "isOverall": false, "label": "/communications/sms [GET} (Cache)-Aggregated", "isController": false}, {"data": [[1.0, 15.5]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.0, 15.5]], "isOverall": false, "label": "Connected Test /  Add Device-Aggregated", "isController": false}, {"data": [[1.0, 26.71428571428571]], "isOverall": false, "label": "Media - Delete", "isController": true}, {"data": [[1.0, 26.71428571428571]], "isOverall": false, "label": "Media - Delete-Aggregated", "isController": true}, {"data": [[1.0, 20.5]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.0, 20.5]], "isOverall": false, "label": "/anf [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 17.875]], "isOverall": false, "label": "/questionnaire [POST]", "isController": false}, {"data": [[1.0, 17.875]], "isOverall": false, "label": "/questionnaire [POST]-Aggregated", "isController": false}, {"data": [[1.0, 9.562499999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.0, 9.562499999999998]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate-Aggregated", "isController": false}, {"data": [[1.0, 13.718750000000002]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.0, 13.718750000000002]], "isOverall": false, "label": "/communications/event [POST]-Aggregated", "isController": false}, {"data": [[1.0, 182.625]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.0, 182.625]], "isOverall": false, "label": "IAM / Change Password-Aggregated", "isController": false}, {"data": [[1.0, 8.375]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.0, 8.375]], "isOverall": false, "label": "/communications/event [DELETE]-Aggregated", "isController": false}, {"data": [[1.0, 8.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.0, 8.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Vendor-Aggregated", "isController": false}, {"data": [[1.0, 9.8125]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.0, 9.8125]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent-Aggregated", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.0, 12.25]], "isOverall": false, "label": "/connected-test/vendor [POST]]-Aggregated", "isController": false}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.0, 17.375]], "isOverall": false, "label": "Routine / CreateMedication-Aggregated", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.0, 11.375]], "isOverall": false, "label": "CommunicationService/listSMSTemplates-Aggregated", "isController": false}, {"data": [[1.0, 78.50000000000001]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.0, 78.50000000000001]], "isOverall": false, "label": "Classification / Classify-Aggregated", "isController": false}, {"data": [[1.0, 16.125]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.0, 16.125]], "isOverall": false, "label": "Media / UpdateMedia-Aggregated", "isController": false}, {"data": [[1.0, 183.125]], "isOverall": false, "label": "Protector", "isController": true}, {"data": [[1.0, 183.125]], "isOverall": false, "label": "Protector-Aggregated", "isController": true}], "supportsControllersDiscrimination": true, "maxX": 1.0, "title": "Time VS Threads"}},
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
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 2024.8833333333334, "minX": 1.7080338E12, "maxY": 49565.583333333336, "series": [{"data": [[1.7080338E12, 6048.5], [1.70803386E12, 49565.583333333336]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.7080338E12, 2024.8833333333334], [1.70803386E12, 14890.766666666666]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70803386E12, "title": "Bytes Throughput Over Time"}},
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
        data: {"result": {"minY": 1.1428571428571428, "minX": 1.7080338E12, "maxY": 1967.0, "series": [{"data": [[1.7080338E12, 12.0], [1.70803386E12, 18.428571428571427]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.7080338E12, 35.0], [1.70803386E12, 14.285714285714286]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.7080338E12, 17.5], [1.70803386E12, 12.60714285714286]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70803386E12, 10.125]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.7080338E12, 32.0], [1.70803386E12, 24.999999999999996]], "isOverall": false, "label": "/ruleset [POST]", "isController": false}, {"data": [[1.7080338E12, 31.25], [1.70803386E12, 10.607142857142852]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 13.571428571428571]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 14.428571428571429]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet", "isController": false}, {"data": [[1.7080338E12, 32.0], [1.70803386E12, 32.285714285714285]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 14.142857142857142]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.7080338E12, 27.0], [1.70803386E12, 12.428571428571429]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 16.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 16.714285714285715]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70803386E12, 18.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 14.035714285714288]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 34.42857142857142]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 10.571428571428571]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.7080338E12, 1967.0], [1.70803386E12, 1413.0]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 20.142857142857142]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.7080338E12, 45.0], [1.70803386E12, 14.857142857142856]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70803386E12, 17.625]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 17.285714285714285]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.7080338E12, 25.75], [1.70803386E12, 6.714285714285714]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 38.0], [1.70803386E12, 17.57142857142857]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 11.714285714285714]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70803386E12, 127.85714285714286]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 22.57142857142857]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 7.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 34.0], [1.70803386E12, 25.571428571428573]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.7080338E12, 234.0], [1.70803386E12, 184.71428571428572]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 9.714285714285714]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.7080338E12, 534.0], [1.70803386E12, 339.57142857142856]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.7080338E12, 2.0], [1.70803386E12, 1.1428571428571428]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.7080338E12, 26.25], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 8.142857142857142]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.7080338E12, 33.0], [1.70803386E12, 20.857142857142858]], "isOverall": false, "label": "/questionnaire [PUT]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 12.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 16.999999999999996]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.7080338E12, 53.0], [1.70803386E12, 31.57142857142857]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.7080338E12, 27.0], [1.70803386E12, 20.857142857142858]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.7080338E12, 762.0], [1.70803386E12, 383.57142857142856]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 17.785714285714285]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 24.428571428571427]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.7080338E12, 41.0], [1.70803386E12, 14.857142857142856]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 12.714285714285715]], "isOverall": false, "label": "/ruleset [GET]]", "isController": false}, {"data": [[1.7080338E12, 162.0], [1.70803386E12, 135.71428571428572]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.7080338E12, 9.5], [1.70803386E12, 13.178571428571427]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70803386E12, 10.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.7080338E12, 259.0], [1.70803386E12, 209.85714285714286]], "isOverall": false, "label": "Questionnaire", "isController": true}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 18.142857142857142]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.7080338E12, 40.0], [1.70803386E12, 33.71428571428571]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 13.428571428571427]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.7080338E12, 35.0], [1.70803386E12, 43.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.7080338E12, 47.0], [1.70803386E12, 13.857142857142858]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 20.57142857142857]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.7080338E12, 33.0], [1.70803386E12, 17.285714285714285]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 14.142857142857144]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 9.428571428571429]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 8.142857142857144]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.7080338E12, 21.0], [1.70803386E12, 24.71428571428571]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 12.428571428571429]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 8.642857142857144]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 99.125]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 15.714285714285715]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.7080338E12, 36.0], [1.70803386E12, 9.857142857142856]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.7080338E12, 20.75], [1.70803386E12, 6.0357142857142865]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.7080338E12, 21.0], [1.70803386E12, 15.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.7080338E12, 57.0], [1.70803386E12, 55.57142857142857]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 10.857142857142856]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.7080338E12, 27.5], [1.70803386E12, 8.928571428571429]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 6.571428571428571]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 13.571428571428571]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.7080338E12, 23.75], [1.70803386E12, 10.178571428571425]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.7080338E12, 24.5], [1.70803386E12, 9.428571428571429]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.7080338E12, 252.0], [1.70803386E12, 96.71428571428571]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.7080338E12, 19.5], [1.70803386E12, 12.000000000000002]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 8.714285714285714]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.7080338E12, 30.0], [1.70803386E12, 31.857142857142858]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.7080338E12, 20.0], [1.70803386E12, 15.285714285714285]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 9.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 37.14285714285714]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.7080338E12, 4.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.7080338E12, 30.0], [1.70803386E12, 25.428571428571427]], "isOverall": false, "label": "Questionnaire / CreateRuleSet", "isController": false}, {"data": [[1.7080338E12, 27.25], [1.70803386E12, 5.75]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 13.142857142857144]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 7.0], [1.70803386E12, 8.357142857142858]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 9.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.7080338E12, 72.0], [1.70803386E12, 47.57142857142857]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 15.714285714285715]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.7080338E12, 30.25], [1.70803386E12, 10.892857142857142]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 64.0], [1.70803386E12, 26.714285714285715]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 12.857142857142858]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70803386E12, 9.499999999999998]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 10.285714285714285]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 14.285714285714285]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 9.857142857142856]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.7080338E12, 445.0], [1.70803386E12, 302.14285714285717]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.7080338E12, 111.0], [1.70803386E12, 102.85714285714286]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 20.57142857142857]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 12.857142857142856]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.7080338E12, 34.0], [1.70803386E12, 23.142857142857142]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.7080338E12, 7.5], [1.70803386E12, 9.5]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.7080338E12, 18.0], [1.70803386E12, 10.428571428571429]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 8.857142857142858]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 10.142857142857144]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 11.85714285714286]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.7080338E12, 16.25], [1.70803386E12, 11.07142857142857]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.7080338E12, 12.5], [1.70803386E12, 11.285714285714286]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 18.57142857142857]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.7080338E12, 6.25], [1.70803386E12, 6.428571428571428]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70803386E12, 177.625]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 12.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 15.285714285714285]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.7080338E12, 7.25], [1.70803386E12, 6.178571428571428]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 16.428571428571427]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 15.0]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 11.571428571428571]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 12.714285714285714]], "isOverall": false, "label": "Questionnaire / GetRuleSets", "isController": false}, {"data": [[1.7080338E12, 10.5], [1.70803386E12, 12.035714285714286]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 6.571428571428572]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 19.857142857142858]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 15.857142857142858]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 16.28571428571429]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.7080338E12, 21.0], [1.70803386E12, 5.5]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 20.428571428571427]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 10.857142857142858]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.7080338E12, 64.0], [1.70803386E12, 57.42857142857142]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 18.42857142857143]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.7080338E12, 19.0], [1.70803386E12, 9.428571428571429]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 13.571428571428571]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 14.714285714285715]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 21.000000000000004]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.7080338E12, 19.0], [1.70803386E12, 12.285714285714286]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 15.857142857142856]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 15.999999999999998]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 13.571428571428571]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 16.000000000000004]], "isOverall": false, "label": "/ruleset/list[GET]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 10.642857142857144]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 12.428571428571429]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 22.285714285714285]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 19.285714285714285]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.7080338E12, 390.0], [1.70803386E12, 306.8571428571429]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 13.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.7080338E12, 26.0], [1.70803386E12, 12.714285714285715]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.7080338E12, 33.0], [1.70803386E12, 13.714285714285715]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 22.142857142857142]], "isOverall": false, "label": "/user/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 570.0], [1.70803386E12, 311.2857142857143]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.7080338E12, 11.0], [1.70803386E12, 11.857142857142858]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 15.142857142857142]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.7080338E12, 187.0], [1.70803386E12, 197.14285714285714]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.7080338E12, 27.0], [1.70803386E12, 24.428571428571427]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 20.142857142857142]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 16.428571428571427]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70803386E12, 10.375]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.7080338E12, 138.0], [1.70803386E12, 124.28571428571429]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 9.142857142857142]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.7080338E12, 27.25], [1.70803386E12, 8.85714285714286]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 12.428571428571427]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 25.999999999999996]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.7080338E12, 15.5], [1.70803386E12, 13.499999999999998]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 10.857142857142858]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70803386E12, 9.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.7080338E12, 255.0], [1.70803386E12, 164.85714285714286]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.7080338E12, 635.0], [1.70803386E12, 285.1428571428571]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70803386E12, 14.285714285714285]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70803386E12, 15.125]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.7080338E12, 19.5], [1.70803386E12, 22.357142857142858]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "Questionnaire / GetRuleSet", "isController": false}, {"data": [[1.7080338E12, 29.75], [1.70803386E12, 11.285714285714286]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 189.0], [1.70803386E12, 185.14285714285714]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70803386E12, 17.142857142857142]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.7080338E12, 103.0], [1.70803386E12, 113.00000000000001]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 20.714285714285715]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 18.142857142857142]], "isOverall": false, "label": "/ruleset [PUT]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 8.571428571428571]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.7080338E12, 30.0], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 11.428571428571429]], "isOverall": false, "label": "/questionnaire [GET]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 12.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 18.714285714285715]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 15.857142857142858]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.7080338E12, 77.0], [1.70803386E12, 33.857142857142854]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 13.857142857142856]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 16.285714285714285]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.7080338E12, 8.5], [1.70803386E12, 6.07142857142857]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.7080338E12, 32.0], [1.70803386E12, 13.142857142857142]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70803386E12, 26.71428571428571]], "isOverall": false, "label": "Media - Delete", "isController": true}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 22.0]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.7080338E12, 20.0], [1.70803386E12, 17.571428571428573]], "isOverall": false, "label": "/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 22.25], [1.70803386E12, 7.749999999999999]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 10.5], [1.70803386E12, 14.178571428571429]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.7080338E12, 188.0], [1.70803386E12, 181.85714285714283]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 7.7142857142857135]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70803386E12, 8.428571428571429]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.7080338E12, 24.0], [1.70803386E12, 7.785714285714286]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 12.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 16.285714285714285]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.7080338E12, 24.75], [1.70803386E12, 9.464285714285717]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70803386E12, 78.50000000000001]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 14.857142857142858]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.7080338E12, 234.0], [1.70803386E12, 175.85714285714286]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70803386E12, "title": "Response Time Over Time"}},
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
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.7080338E12, "maxY": 899.0, "series": [{"data": [[1.7080338E12, 12.0], [1.70803386E12, 18.0]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.7080338E12, 17.5], [1.70803386E12, 12.392857142857142]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70803386E12, 10.0]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 24.42857142857143]], "isOverall": false, "label": "/ruleset [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 15.857142857142858]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.7080338E12, 11.75], [1.70803386E12, 13.642857142857142]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 34.285714285714285]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.7080338E12, 899.0], [1.70803386E12, 802.8571428571428]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 19.714285714285715]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70803386E12, 17.25]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 17.285714285714285]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 38.0], [1.70803386E12, 17.428571428571427]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 11.571428571428571]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70803386E12, 63.00000000000001]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 22.285714285714285]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.7080338E12, 233.0], [1.70803386E12, 184.71428571428572]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.7080338E12, 131.0], [1.70803386E12, 193.42857142857142]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 8.142857142857142]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.7080338E12, 33.0], [1.70803386E12, 19.857142857142858]], "isOverall": false, "label": "/questionnaire [PUT]", "isController": false}, {"data": [[1.7080338E12, 27.0], [1.70803386E12, 11.571428571428571]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 18.428571428571427]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.7080338E12, 284.0], [1.70803386E12, 206.85714285714286]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 17.571428571428573]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 24.142857142857142]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 12.714285714285715]], "isOverall": false, "label": "/ruleset [GET]]", "isController": false}, {"data": [[1.7080338E12, 50.0], [1.70803386E12, 73.85714285714285]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.7080338E12, 9.5], [1.70803386E12, 12.785714285714285]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.7080338E12, 155.0], [1.70803386E12, 140.7142857142857]], "isOverall": false, "label": "Questionnaire", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 20.285714285714285]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.7080338E12, 20.0], [1.70803386E12, 24.428571428571427]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 11.857142857142856]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 98.87500000000001]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 15.571428571428571]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.7080338E12, 18.0], [1.70803386E12, 14.714285714285715]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.7080338E12, 57.0], [1.70803386E12, 55.42857142857142]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 13.285714285714285]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.7080338E12, 19.25], [1.70803386E12, 11.821428571428571]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 8.428571428571429]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 31.571428571428573]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.7080338E12, 18.0], [1.70803386E12, 15.0]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.7080338E12, 8.5], [1.70803386E12, 8.714285714285715]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.7080338E12, 28.0], [1.70803386E12, 36.285714285714285]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / CreateRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 12.857142857142858]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 6.75], [1.70803386E12, 8.142857142857142]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 9.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.7080338E12, 72.0], [1.70803386E12, 47.42857142857143]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70803386E12, 9.375]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.7080338E12, 11.0], [1.70803386E12, 9.857142857142856]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.7080338E12, 129.0], [1.70803386E12, 150.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 20.000000000000004]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.7080338E12, 7.5], [1.70803386E12, 9.214285714285712]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.7080338E12, 18.0], [1.70803386E12, 10.428571428571429]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 8.714285714285715]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 13.142857142857142]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 10.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 13.75], [1.70803386E12, 11.607142857142856]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.7080338E12, 16.25], [1.70803386E12, 10.857142857142856]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.7080338E12, 12.5], [1.70803386E12, 11.142857142857144]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 18.428571428571427]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.7080338E12, 6.0], [1.70803386E12, 6.357142857142858]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70803386E12, 98.87500000000001]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 15.0]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.7080338E12, 7.25], [1.70803386E12, 5.821428571428572]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 16.428571428571427]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 14.857142857142856]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / GetRuleSets", "isController": false}, {"data": [[1.7080338E12, 10.5], [1.70803386E12, 11.999999999999998]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 19.142857142857142]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 12.0], [1.70803386E12, 20.0]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.7080338E12, 29.0], [1.70803386E12, 31.571428571428573]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.7080338E12, 19.0], [1.70803386E12, 9.142857142857142]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 14.428571428571429]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.7080338E12, 17.0], [1.70803386E12, 11.857142857142858]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.7080338E12, 9.0], [1.70803386E12, 13.428571428571427]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 15.428571428571429]], "isOverall": false, "label": "/ruleset/list[GET]", "isController": false}, {"data": [[1.7080338E12, 8.5], [1.70803386E12, 10.571428571428571]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 12.285714285714286]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 21.714285714285715]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.7080338E12, 161.0], [1.70803386E12, 187.99999999999997]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.7080338E12, 23.0], [1.70803386E12, 22.0]], "isOverall": false, "label": "/user/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 145.0], [1.70803386E12, 166.14285714285714]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.7080338E12, 11.0], [1.70803386E12, 11.428571428571427]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 14.857142857142858]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.7080338E12, 187.0], [1.70803386E12, 197.14285714285714]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.7080338E12, 27.0], [1.70803386E12, 24.0]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 19.57142857142857]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 16.285714285714285]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70803386E12, 10.0]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.7080338E12, 137.0], [1.70803386E12, 124.0]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.7080338E12, 8.0], [1.70803386E12, 9.035714285714286]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 12.0]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.7080338E12, 25.0], [1.70803386E12, 25.285714285714285]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 13.357142857142858]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 10.714285714285714]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.7080338E12, 145.0], [1.70803386E12, 113.57142857142857]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.7080338E12, 221.0], [1.70803386E12, 153.85714285714286]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70803386E12, 14.874999999999998]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.7080338E12, 19.5], [1.70803386E12, 22.357142857142858]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / GetRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 63.0], [1.70803386E12, 89.14285714285715]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.7080338E12, 42.0], [1.70803386E12, 52.0]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 19.571428571428573]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 16.0], [1.70803386E12, 18.0]], "isOverall": false, "label": "/ruleset [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.7080338E12, 15.0], [1.70803386E12, 11.142857142857142]], "isOverall": false, "label": "/questionnaire [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.7080338E12, 11.0], [1.70803386E12, 18.285714285714285]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 15.714285714285714]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 13.428571428571429]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.7080338E12, 8.25], [1.70803386E12, 5.857142857142855]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70803386E12, 12.0]], "isOverall": false, "label": "Media - Delete", "isController": true}, {"data": [[1.7080338E12, 10.0], [1.70803386E12, 21.42857142857143]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.7080338E12, 20.0], [1.70803386E12, 17.142857142857142]], "isOverall": false, "label": "/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 10.25], [1.70803386E12, 14.07142857142857]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.7080338E12, 13.0], [1.70803386E12, 7.571428571428571]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 14.0], [1.70803386E12, 12.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.7080338E12, 95.0], [1.70803386E12, 77.14285714285714]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70803386E12, "title": "Latencies Over Time"}},
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
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.7080338E12, "maxY": 31.0, "series": [{"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/device [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Device", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event/list [POST]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/ruleset [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/SendNotification", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / DetectAnomalies", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media [PUT]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / ProtectPrivacy", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Device", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById", "isController": false}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 14.571428571428571]], "isOverall": false, "label": "IAM", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/medication [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabResult", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/organization [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/country [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/country [PUT]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test - Delete", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / CreateMedia", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/diagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Predictor / Predict", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user/login {POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Device", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Communications Low", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Thread Setup", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms [DELETE}", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/questionnaire [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/workspace/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Get User Profile", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Audit", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / GetANFStatement", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Communications Immediate", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media/upload", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/anf [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Create Workspace", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/ruleset [GET]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test - Connected Tests", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms/list [POST]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / CreateANFStatement", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update Workspace", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update User Profile", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Get User", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update User", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Update Organization", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / GetMedia", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/classify [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/workspace [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/labOrder [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/device [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listNotificationEvents", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listEmailTemplates", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Signup", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event [PUT]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/predictor [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms [GET}", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/profile [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0]], "isOverall": false, "label": "Properties Details", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / CreateRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/organization/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Create Organization", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  SubmitTest", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateDiagnosis", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/country [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/country [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Login", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/labResult [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / UpdateANFStatement", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/workspace/{id} [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/event/audit", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email [GET] (Cache)", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Classification", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Current User", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user [GET]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event [GET] (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / GetRuleSets", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/workspace [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  ListConnectedTests", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateDeliveryTracking", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Predictor", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateLabOrder", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user/current [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/protector/authorize [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Country", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateRoutine", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/ruleset/list[GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media/download", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/device [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/organization [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "AuditService / Event", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / ListMedia", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/user/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Communications High", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/testcase [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user/password [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/list [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/media/list  [POST]]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [DELETE]", "isController": false}, {"data": [[1.7080338E12, 31.0], [1.70803386E12, 14.571428571428571]], "isOverall": false, "label": "/iam/user/signup {POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/email [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/media  [DELETE]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/anf [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/notification [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [PUT]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Communications Medium", "isController": true}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Media / DeleteMedia", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/device [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/user/verify [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Questionnaire / GetRuleSet", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF", "isController": true}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM Profile", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/ruleset [PUT]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Get TestCase", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / List Organization", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/questionnaire [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Update Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/anf [GET]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/profile [GET]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / List Users", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/iam/organization/list [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "ANF / DeleteANFStatement", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/sms [GET} (Cache)", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Add Device", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Media - Delete", "isController": true}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/anf [DELETE]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/questionnaire [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event [POST]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "IAM / Change Password", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/communications/event [DELETE]", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Connected Test /  Delete Vendor", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "/connected-test/vendor [POST]]", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Routine / CreateMedication", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "CommunicationService/listSMSTemplates", "isController": false}, {"data": [[1.70803386E12, 0.0]], "isOverall": false, "label": "Classification / Classify", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Media / UpdateMedia", "isController": false}, {"data": [[1.7080338E12, 0.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Protector", "isController": true}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70803386E12, "title": "Connect Time Over Time"}},
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
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 0.0, "minX": 1.7080338E12, "maxY": 252.0, "series": [{"data": [[1.7080338E12, 252.0], [1.70803386E12, 245.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.7080338E12, 35.0], [1.70803386E12, 26.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.7080338E12, 199.95999999999958], [1.70803386E12, 170.0]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.7080338E12, 42.900000000000034], [1.70803386E12, 38.0]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.7080338E12, 2.0], [1.70803386E12, 0.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.7080338E12, 22.0], [1.70803386E12, 11.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70803386E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
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
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 4.0, "minX": 1.0, "maxY": 138.0, "series": [{"data": [[40.0, 13.0], [41.0, 13.0], [42.0, 15.0], [43.0, 13.0], [45.0, 19.0], [44.0, 15.5], [3.0, 138.0], [49.0, 15.0], [52.0, 13.5], [54.0, 14.5], [57.0, 13.0], [60.0, 13.5], [62.0, 12.5], [67.0, 11.0], [65.0, 12.0], [71.0, 12.0], [76.0, 11.0], [77.0, 12.0], [5.0, 36.0], [90.0, 9.0], [95.0, 9.0], [6.0, 25.0], [99.0, 9.0], [107.0, 8.0], [104.0, 8.0], [109.0, 8.0], [7.0, 24.0], [127.0, 7.0], [10.0, 25.5], [11.0, 25.0], [12.0, 22.0], [13.0, 24.0], [1.0, 4.0], [16.0, 34.5], [17.0, 17.0], [18.0, 20.0], [19.0, 13.0], [20.0, 12.0], [21.0, 13.0], [24.0, 10.0], [25.0, 14.5], [27.0, 15.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 127.0, "title": "Response Time Vs Request"}},
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
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 0.0, "minX": 1.0, "maxY": 16.0, "series": [{"data": [[40.0, 8.0], [41.0, 2.5], [42.0, 0.0], [43.0, 0.0], [45.0, 13.0], [44.0, 9.5], [3.0, 0.0], [49.0, 9.0], [52.0, 0.0], [54.0, 10.0], [57.0, 8.0], [60.0, 10.0], [62.0, 9.0], [67.0, 4.0], [65.0, 0.0], [71.0, 8.0], [76.0, 6.0], [77.0, 8.0], [5.0, 0.0], [90.0, 5.0], [95.0, 5.0], [6.0, 0.0], [99.0, 5.0], [107.0, 6.0], [104.0, 0.0], [109.0, 0.0], [7.0, 0.0], [127.0, 4.0], [10.0, 12.5], [11.0, 16.0], [12.0, 11.5], [13.0, 9.0], [1.0, 0.0], [16.0, 0.0], [17.0, 12.0], [18.0, 0.0], [19.0, 9.0], [20.0, 6.5], [21.0, 11.0], [24.0, 7.0], [25.0, 0.0], [27.0, 0.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 127.0, "title": "Latencies Vs Request"}},
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
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 4.566666666666666, "minX": 1.7080338E12, "maxY": 33.46666666666667, "series": [{"data": [[1.7080338E12, 4.566666666666666], [1.70803386E12, 33.46666666666667]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70803386E12, "title": "Hits Per Second"}},
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
        data: {"result": {"minY": 4.55, "minX": 1.7080338E12, "maxY": 33.483333333333334, "series": [{"data": [[1.7080338E12, 4.55], [1.70803386E12, 33.483333333333334]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.70803386E12, "title": "Codes Per Second"}},
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
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 0.016666666666666666, "minX": 1.7080338E12, "maxY": 0.4666666666666667, "series": [{"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/anf [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/questionnaire [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Create Organization-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/anf [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Get Country-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/UpdateEmailTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/labOrder [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/CreateNotificationEvent-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/country [POST]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Delete Manufacturer-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM Profile-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Get User Profile-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector / DetectAnomalies-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "ANF / DeleteANFStatement-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/testcase [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/list [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/protector/detectAnomalies [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Update Country-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/event [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/communications/event [DELETE]-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/connected-test/testcase [DELETE]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Delete Device-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/event/audit-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "CommunicationService/DeleteNotificationEvent-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/email [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/communications/sms [DELETE}-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/CreateSMSTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/media/upload-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateRoutine-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/listNotificationEvents-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Communications Low-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media / GetMedia-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateClinicalProtocolExecution-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/ruleset [GET]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  GetTestDetailsById-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/protector/authorize [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Change Password-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector / MonitorRealTimeActivity-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Get TestCase-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/protector/protectPrivacy [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/organization [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Questionnaire-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Add Manufacturer-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Get Manufacturer-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/email [GET] (Cache)-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media / ListMedia-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/iam/user/verify [GET]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Delete TestCase-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Create Workspace-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/deliveryTracking [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/device [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/clinicalProtocolExecution [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/country [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/user/questionnaire [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector / AnalyzeUserBehavior-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/workspace/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/protector/analyzeUserBehavior [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/country [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / List Organization-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "CommunicationService/DeleteEmailTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/event [PUT]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Current User-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test - Devices Vendor Manufacturers TestCases-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/organization/{id} [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user/login {POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Audit-success", "isController": true}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/UpdateSMSTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/communications/email [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666]], "isOverall": false, "label": "Properties Details-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Questionnaire / CreateRuleSet-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/SendNotification-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate (Cache)-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/sms [GET} (Cache)-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/notification [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test - Connected Tests-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "CommunicationService/DeleteSMSTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Communications High-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateMedication-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/testcase [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Signup-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user [GET]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Thread Setup-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Add Vendor-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user/signup {POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/sms/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Get Vendor-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media / CreateMedia-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Update Manufacturer-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Predictor / Predict-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/media  [DELETE]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Update TestCase-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Add Country-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Questionnaire / GetRuleSet-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/profile [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/listSMSTemplates-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateDiagnosis-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "/media/download-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/organization [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  ListConnectedTests-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "ANF / CreateANFStatement-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.03333333333333333], [1.70803386E12, 0.23333333333333334]], "isOverall": false, "label": "IAM / Update Workspace-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/sms [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate (Cache)-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/connected-test/vendor [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "AuditService / Event-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/labResult [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Get User-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/anf [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Update User-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/questionnaire [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/media [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Update Vendor-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/email/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/ruleset/list[GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "ANF / UpdateANFStatement-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetEmailTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/UpdateNotificationEvent-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/email [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/testcase [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateLabResult-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/manufacturer [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/email [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Questionnaire / UpdateRuleSet-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector / ProtectPrivacy-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Communications Immediate-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media / UpdateMedia-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Predictor-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/medication [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/event/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Get Device-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/classify [POST]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Delete Country-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/ruleset [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/diagnosis [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/event [GET] (Cache)-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / List Users-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/sms [POST]-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "Classification / Classify-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/media/list  [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user/password [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/device [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/sms [GET}-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Communications Medium-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/manufacturer [GET]-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/connected-test/manufacturer [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/workspace [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/vendor [PUT]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "ANF-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/workspace/{id} [GET]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Delete Vendor-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Add Device-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/anf [POST]]-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media - Delete-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/vendor [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/manufacturer [PUT]-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/connected-test/country [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine/suspectedDiagnosis [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateLabOrder-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Questionnaire / GetRuleSets-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user/current [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/routine [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/user/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/media [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Protector / EnforceAuthorizationControl-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/vendor [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/connected-test/device [GET]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/media [PUT]]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "/communications/event [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/ruleset [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "ANF / GetANFStatement-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/protector/monitorRealTime [POST]]-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "Classification-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/organization/list [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetNotificationEvent (Cache)-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media / DeleteMedia-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Login-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Media-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM-success", "isController": true}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  SubmitTest-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateSuspectedDiagnosis-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/workspace [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / GetWorkspaceDetailsById-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Update User Profile-success", "isController": false}, {"data": [[1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test - Delete-success", "isController": true}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/GetSMSTemplate-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Routine / CreateDeliveryTracking-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Add TestCase-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/listEmailTemplates-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "Connected Test /  Update Device-success", "isController": false}, {"data": [[1.70803386E12, 0.13333333333333333]], "isOverall": false, "label": "/connected-test/device [DELETE]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / Update Organization-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/questionnaire [POST]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "IAM / GetOrganizationDetailsById-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/predictor [POST]]-success", "isController": false}, {"data": [[1.7080338E12, 0.016666666666666666], [1.70803386E12, 0.11666666666666667]], "isOverall": false, "label": "/iam/profile [GET]]-success", "isController": false}, {"data": [[1.7080338E12, 0.06666666666666667], [1.70803386E12, 0.4666666666666667]], "isOverall": false, "label": "CommunicationService/CreateEmailTemplate-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70803386E12, "title": "Transactions Per Second"}},
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
};

var totalTPSInfos = {
        data: {"result": {"minY": 4.8, "minX": 1.7080338E12, "maxY": 35.6, "series": [{"data": [[1.7080338E12, 4.8], [1.70803386E12, 35.6]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.70803386E12, "title": "Total Transactions Per Second"}},
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
};

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
