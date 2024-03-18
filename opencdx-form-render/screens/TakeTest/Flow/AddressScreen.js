import React, { useState } from 'react';
import { View, Text, FlatList, TouchableOpacity } from 'react-native';
import { SafeAreaView } from 'react-native';
import { Ionicons, AntDesign, MaterialCommunityIcons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';

const AddressSection = () => {
    const navigation = useNavigation();
    const [testList, setTestList] = useState([
        { id: 1, name: 'Home', description: '743 El Camino Real, Sunnyvale, CA 94087' },
        { id: 2, name: 'Work', description: '123 Main Street, San Jose, CA 95131' },
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
                                <Text style={styles.description}>{item.name}</Text>
                                <Text style={styles.subText}>{item.description}</Text>
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
                        <Text style={styles.description}>Add an Address</Text>
                        <Text style={styles.subText}>Save your favorite places</Text>
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
        justifyContent: 'space-between',
    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'left',
        paddingLeft: 10,
        paddingVertical: 15,
    },
    sectionWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingVertical: 10,
        borderBottomWidth: 1,
        borderBottomColor: '#ccc',
    },
    leftSection: {
        flex: 1,
    },
    description: {
        fontWeight: 'bold',
    },
    subText: {
        color: '#888',
    },
    rightSection: {
        marginLeft: 10,
        paddingTop: 2,
    },
};


export default AddressSection;
