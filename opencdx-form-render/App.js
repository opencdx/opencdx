import { StyleSheet, View, Platform } from 'react-native';
import Constants from 'expo-constants';
import FormRender from './components/FormRender';
import { GluestackUIProvider, ScrollView } from '@gluestack-ui/themed';
import { config } from '@gluestack-ui/config';

export default function App() {

  return (
    <View style={styles.container}>
        <GluestackUIProvider config={config}>
            { Platform.OS === "web" ? (
              <FormRender/>
            ) : (
              <ScrollView h="$80" w="$100">
                <FormRender/>
              </ScrollView>
            )}
      </GluestackUIProvider>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingTop: Constants.statusBarHeight,
    padding: 8,
    maxWidth: 500,
  },
});
