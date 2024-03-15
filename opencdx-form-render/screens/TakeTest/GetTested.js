import React from 'react';
import { View, StyleSheet, Platform, ScrollView } from 'react-native';
import {  Text } from 'react-native-paper';

import AnimatedCard from '../../components/AnimatedCard';

const GetTested = ({ navigation }) => {

    return (
        <View style={styles.container}>
            <ScrollView
                showsHorizontalScrollIndicator={false}
                style={styles.body}
                contentContainerStyle={{
                    paddingVertical: 10,
                    paddingHorizontal: 16,
                }}>
                <Text variant='titleMedium'>
                    Select how you would like to get tested            </Text>
                    <AnimatedCard navigation={navigation} link='ScheduleAppointment' src={require('../../assets/schedule.json')} title='Schedule Appointment' subtitle='Schedule an appointment to get tested' />
                    <AnimatedCard navigation={navigation} link='TestKit' src={require('../../assets/test.json')} title='I have a Test Kit' subtitle='I have a test kit and need to submit my results' />
                    <AnimatedCard navigation={navigation} link='TestList' src={require('../../assets/lab2.json')} title='Order a Test Kit' subtitle='I need to order a test kit' />
            </ScrollView>
        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                maxWidth: 350,
            },
            default: {
                margin: 20,
               
            },
        })),
    },

    body: {
        
        
    },
});

export default GetTested;
