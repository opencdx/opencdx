import React, { useState } from 'react';
import { View, Text, FlatList, TextInput, TouchableOpacity } from 'react-native';
import { SafeAreaView } from 'react-native';
import { Platform } from 'react-native';

import { Ionicons, AntDesign } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import { MaterialCommunityIcons } from '@expo/vector-icons';


const AddressSection = () => {
    const navigation = useNavigation();
    const [testList, setTestList] = useState([
        { id: 1, name: 'Home', description: '743 El Camino Real, Sunnyvale, CA 94087' },
        { id: 2, name: 'Work', description: '123 Main Street, San Jose, CA 95131' },

        // Add more test items here
    ]);



    const handleBack = () => {
        navigation.goBack();
    };

    return (
        <View style={styles.container}>
            <SafeAreaView>
                <View style={styles.searchContainer}>
                    <TouchableOpacity onPress={handleBack}>
                        <Ionicons name="arrow-back" size={24} color="black" />
                    </TouchableOpacity>

                </View>

                <FlatList
                    data={testList}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.sectionWrapper}>
                            <View style={styles.leftSection}>
                                <Text style={styles.description}
                                >{item.name}</Text>
                                <Text style={styles.subText}
                                >{item.description}</Text>
                            </View>
                            <View style={styles.rightSection}>

                                <TouchableOpacity style={{ marginLeft: 10, paddingTop: 2 }}
                                    onPress={() => navigation.navigate('TestDetails', { test: item })}>
                                    {item.name === 'Home' ? <AntDesign name="home" size={24} color="black" /> : <MaterialCommunityIcons name="office-building" size={24} color="black" />}
                                </TouchableOpacity>
                            </View>
                        </View>
                    )}
                />
                <View style={styles.sectionWrapper}>
                    <View style={styles.leftSection}>
                        <Text style={styles.description}
                        >Add an Address</Text>
                        <Text style={styles.subText}
                        >Save your favorite places</Text>
                    </View>

                </View>
            </SafeAreaView>
        </View>
    );
};

const styles = {
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        padding: 10,
        ...Platform.select({
            web: {
                width: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    popularTest: {
        backgroundColor: '#ebf0f5',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'left',
        paddingLeft: 10,
        paddingVertical: 15,
    },
    popularText: {

        fontWeight: 'bold',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 24,
            }
        })
    },
    description: {
        fontWeight: 'bold',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 18,
            }
        })
    },
    subText:
    {
        color: 'gray',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 16,
            }
        })
    },
    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        paddingVertical: 5,


    },
    leftSection: {
        padding: 10,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',


    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',



    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,

    },
    searchInput: {
        flex: 1,
        marginLeft: 10,
        height: 40,
        borderColor: 'white',
        borderWidth: 1,
        borderRadius: 5,
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 10,
        paddingHorizontal: 10,
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                width: 400,
            },
            default: {
                width: '100%',
            }
        })
    },
};

export default AddressSection;
