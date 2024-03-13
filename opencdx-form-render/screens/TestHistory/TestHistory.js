import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text } from 'react-native-paper';


const TestHistory = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <View>
                <Text style={styles.title} variant="titleLarge" size="md">
                    2024
                </Text>
                <View style={styles.section}>
                    <Text variant="titleMedium" style={styles.paragraph}>Vaccine Type: Moderna/Spikevax</Text>
                    <Text variant="titleMedium"  style={styles.paragraph}>Date: 2024-03-03</Text>
                    <Text variant="titleMedium"  style={styles.paragraph}>Health Professional : CVS Parmacy</Text>
                    <Text variant="titleMedium" style={styles.paragraph}>Country Administrated: United States</Text>
                </View>
                <View style={styles.inter}></View>
                <View style={styles.section}>
                    <Text variant="titleMedium" style={styles.paragraph}>Vaccine Type: Moderna/Spikevax</Text>
                    <Text variant="titleMedium" style={styles.paragraph}>Date: 2024-03-03</Text>
                    <Text variant="titleMedium" style={styles.paragraph}>Health Professional : CVS Parmacy</Text>
                    <Text variant="titleMedium" style={styles.paragraph}>Country Administrated: United States</Text>
                </View>
            </View>
            <View style={styles.footer}>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                    Add a record                </Button>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                    Go Back                </Button>
                
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
    inter: {
        padding: 10,
        backgroundColor: 'white',
        marginBottom: 10,
    },
    section: {
        padding: 10,
        marginHorizontal: 10,
        backgroundColor: 'paleturquoise',
    },
    title: {
        marginTop: 10,
        marginBottom: 10,
        color: 'black',
        fontWeight: 'bold',
        fontSize: 18,
        marginLeft: 10,
    },
    button: {
        marginTop: 10,
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
    buttonText: {
        color: 'white',
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
});

export default TestHistory;
