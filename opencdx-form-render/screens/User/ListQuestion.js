import React from 'react';
import { StyleSheet, View, Platform } from 'react-native';
import Constants from 'expo-constants';
import FormRender from '../../components/FormRender';
import { Center, ScrollView } from '@gluestack-ui/themed';

const ListQuestion = ({route,navigation}) => {
    const { questionnaire } = route.params;
    return (
        <View style={styles.container}>
            { Platform.OS === "web" ? (
                <Center>
                    <FormRender questionnaire={questionnaire} navigation={navigation}/>
                </Center>
            ) : (
                <ScrollView h="$80" w="$100">
                    <FormRender questionnaire={questionnaire} navigation={navigation}/>
                </ScrollView>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        paddingTop: Constants.statusBarHeight,
        padding: 8,
        ...Platform.select({
            web: {

            },
            default: {
                maxWidth: 500,
            }
        })
    },
});

export default ListQuestion;