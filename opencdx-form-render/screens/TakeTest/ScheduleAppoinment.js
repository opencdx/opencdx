import React, { useState } from 'react';
import { SafeAreaView, View, StyleSheet, Platform } from 'react-native';
import { Calendar } from 'react-native-calendars';
import { Button } from 'react-native-paper';

const ScheduleAppointment = ({ navigation }) => {
    const [selected, setSelected] = useState('');
    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Calendar
                    onDayPress={day => {
                        setSelected(day.dateString);
                    }}
                    markedDates={{
                        [selected]: { selected: true, disableTouchEvent: true, selectedDotColor: 'orange' }
                    }}
                />
            </View>
            <View style={styles.footer}>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                   Schedule Appointment              </Button>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            }
        })
    },
    body: {
        ...Platform.select({
            web: {
                padding: 24,
            },
            default: {
                flex: 1,
                justifyContent: 'center',
                padding: 24,
            }
        })
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
});

export default ScheduleAppointment;
