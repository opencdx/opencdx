import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text, Divider, Card } from 'react-native-paper';


const TestHistory = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <View>
                <Text style={styles.title} variant="titleLarge" size="md">
                    2024
                </Text>
                <Card>
                    <Card.Content style={styles.section}>
                        <Text variant="titleMedium">Vaccine Type: Moderna/Spikevax</Text>
                        <Text variant="titleMedium">Date: 2024-03-03</Text>
                        <Text variant="titleMedium">Health Professional : CVS Parmacy</Text>
                        <Text variant="titleMedium">Country Administrated: United States</Text>
                    </Card.Content>
                   
                </Card>
                <Divider style={{ margin: 10 }} />
               
                <Card>
                    <Card.Content style={styles.section}>
                        <Text variant="titleMedium">Vaccine Type: Moderna/Spikevax</Text>
                        <Text variant="titleMedium">Date: 2024-03-03</Text>
                        <Text variant="titleMedium">Health Professional : CVS Parmacy</Text>
                        <Text variant="titleMedium">Country Administrated: United States</Text>
                    </Card.Content>

                </Card>
        
            </View>
            <View style={styles.footer}>
                <Button mode="contained-tonal"  title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                    Add a record                </Button>
                <Button mode="contained-tonal"  title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                    Go Back                </Button>
                
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        margin: 10,
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
   
    
    title: {
        marginTop: 10,
        marginBottom: 10,
        color: 'black',
        fontWeight: 'bold',
        fontSize: 18,
    },
    button: {
        marginTop: 10,
        alignItems: 'center',
        justifyContent: 'center',
        width: '90%',
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
