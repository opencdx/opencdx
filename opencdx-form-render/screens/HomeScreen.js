import React from 'react';
import { StyleSheet, View, Platform } from 'react-native';
import Constants from 'expo-constants';
import FormRender from '../components/FormRender';
import { Center, GluestackUIProvider, ScrollView } from '@gluestack-ui/themed';
import { config } from '@gluestack-ui/config';


const HomeScreen = ({route}) => {
    const { questionnaire } = route.params;
    return (
        <View style={styles.container}>
                <GluestackUIProvider config={config}>
                        { Platform.OS === "web" ? (
                            <Center>
                                <FormRender questionnaire={questionnaire}/>
                            </Center>
                        ) : (
                            <ScrollView h="$80" w="$100">
                                <FormRender questionnaire={questionnaire}/>
                            </ScrollView>
                        )}
            </GluestackUIProvider>
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

export default HomeScreen;