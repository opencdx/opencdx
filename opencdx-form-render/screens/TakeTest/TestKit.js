import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text } from 'react-native-paper';

const TestKit = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Text style={styles.text} variant='titleMedium'
>
                    Remove test device from pouch and hold your camera over the activation Code
                </Text>
            </View>
            <View style={styles.footer}>
                <View style={styles.center}>
                    <Text variant='titleMedium'
 style={styles.signup}>
                        Contact Support
                    </Text>
                </View>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
Scan Code                </Button>
               
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
    text: {
        marginBottom: 24,
        textAlign: 'center',
    },
    input: {
        textAlign: 'center',
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
    signup: {
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "center",
        paddingBottom: 24,
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
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
});

export default TestKit;
