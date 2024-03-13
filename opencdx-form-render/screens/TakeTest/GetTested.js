import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text } from 'react-native-paper';

const GetTested = ({navigation}) => {
    return (
        <View style={styles.container}>
            <SafeAreaView style={styles.body}>
            <Text  variant='titleMedium'>
Select how you would like to get tested            </Text>
                <Button 
                style={styles.button}
                icon="arrow-right"
                contentStyle={{flexDirection: 'row-reverse'}}
                
                 mode='contained-tonal'   onPress={() => navigation.navigate('TestKit')}>
                I have a Test Kit
            </Button>
            <Button
                    style={styles.button}

            icon="arrow-right"
            contentStyle={{flexDirection: 'row-reverse'}}
            mode='contained-tonal' onPress={() => navigation.navigate('TestList')}>
                Order a Test Kit
            </Button>
            <Button
                    style={styles.button}

            icon="arrow-right"
            contentStyle={{flexDirection: 'row-reverse'}}
            mode='contained-tonal' onPress={() => navigation.navigate('ScheduleAppointment')}>
                Schedule Appointment
            </Button>
            
            </SafeAreaView>
        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin: 20,
                shadowColor: "#000",
            },
        })),
    },
   
   button: {

        marginTop: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        
    },
   
});

export default GetTested;
